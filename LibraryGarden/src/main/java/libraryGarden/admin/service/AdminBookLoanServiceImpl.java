package libraryGarden.admin.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookLoanMapper;
import libraryGarden.domain.ReservationDto;

@Service
public class AdminBookLoanServiceImpl implements AdminBookLoanService{
	
    @Autowired
    private AdminBookLoanMapper adminBookLoanMapper;
    
    
    @Override
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) {
        // — 자동 연체 감지: overdue 테이블만 채우고, 도서 상태는 그대로 둠 —
    	adminBookLoanMapper.insertOverdueForPastDue();

        // (기존) UI에 보여줄 ‘이용가능/이용불가’ 여부
        String userName   = adminBookLoanMapper.selectUserName(userNumber);
        String loanStatus = adminBookLoanMapper.selectUserLoanStatus(userNumber);

        // 페이징 & 대출 목록 조회
        int startPageNum = (page - 1) * perPageNum;
        Map<String,Object> params = new HashMap<>();
        params.put("userNumber",  userNumber);
        params.put("startPageNum", startPageNum);
        params.put("perPageNum",   perPageNum);

        List<Map<String,Object>> loanList = adminBookLoanMapper.selectUserLoanList(params);
        int totalCount = adminBookLoanMapper.selectUserLoanTotalCount(userNumber);

        Map<String,Object> result = new HashMap<>();
        result.put("userName",   userName);
        result.put("loanStatus", loanStatus);
        result.put("loanList",   loanList);
        result.put("totalCount", totalCount);
        return result;
    }
    
    @Override
    public void addBookLoan(String userNumber, String code) throws Exception {
        
        // — (C) 연체 중이면 대출 불가 —
        int odCnt = adminBookLoanMapper.selectActiveOverdueCount(userNumber);
        if (odCnt > 0) {
            throw new IllegalStateException("연체 중인 회원은 대출할 수 없습니다.");
        }
    	
    	// 1) code → lbidx
        int lbidx = adminBookLoanMapper.selectLbidxByCode(code);

        // 2) 예약대기 상태인 경우, 오늘 픽업예약자만 허용
        String status = adminBookLoanMapper.selectBookStatusByLbidx(lbidx);
        if ("예약대기".equals(status)) {
            Map<String,Object> params = new HashMap<>();
            params.put("lbidx", lbidx);
            params.put("userNumber", userNumber);
            ReservationDto res = adminBookLoanMapper.selectActiveReservation(params);
            String today = LocalDate.now().toString();
            if (res == null || !today.equals(res.getPickupDate())) {
                throw new IllegalStateException("오늘 픽업 가능한 예약자가 아닙니다.");
            }
        }
        String today = LocalDate.now().toString();

        // 3) 대출등록 & 도서상태 → 대출중
        adminBookLoanMapper.insertBookLoan(userNumber, code);
        adminBookLoanMapper.updateLibraryBookStatusToLoan(code);
        adminBookLoanMapper.updateReservationToReceived(lbidx, userNumber, today);
    }
    
    @Override
    public boolean isUserOverdue(String userNumber) throws Exception {
        String loanStatus = adminBookLoanMapper.selectUserLoanStatus(userNumber);
        // '이용불가'라는 문자열이 포함되어 있으면 연체 중으로 간주
        return loanStatus.contains("이용불가");
    }
    
    // 삭제
    @Override
    public void deleteLoan(int lidx) throws Exception {
        // 대여 삭제
        adminBookLoanMapper.deleteLoan(lidx);
        // 도서 상태 대출 가능으로 변경
        adminBookLoanMapper.updateLibraryBookStatusToAvailable(lidx);
    }
    
    @Override
    public void returnBookLoan(int lidx) throws Exception {
        // 1) 반납예정일 조회 & 오늘 날짜 비교 (시간 제거)
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate = adminBookLoanMapper.selectDueDate(lidx);
        Date today   = fmt.parse(fmt.format(new Date()));

        if (today.after(fmt.parse(fmt.format(dueDate)))) {
            // 2) 연체일수 계산 후 penalty = 연체일수 *2 +1
            long lateDays = ( today.getTime() - fmt.parse(fmt.format(dueDate)).getTime() )
                             / (1000L * 60 * 60 * 24);
            int penaltyDays = (int)lateDays * 2 + 1;

            // 3) OVERDUE.endDate = 오늘 + penaltyDays
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.DATE, penaltyDays);
            adminBookLoanMapper.updateOverdueEndDate(lidx, cal.getTime());

            // 4) LOAN.status = '연체반납'
            adminBookLoanMapper.updateLoanStatusToReturned(lidx, "연체반납");
        } else {
            // 정상반납
            adminBookLoanMapper.updateLoanStatusToReturned(lidx, "반납완료");
        }

        // 5) LIBRARYBOOKS.status = '대출가능'
        adminBookLoanMapper.updateLibraryBookStatusToAvailable(lidx);
    }

    @Override
    public String getBookStatus(String code) throws Exception {
        String status = adminBookLoanMapper.selectBookStatus(code);
        return status == null ? "없는 도서" : status;
    }

}
