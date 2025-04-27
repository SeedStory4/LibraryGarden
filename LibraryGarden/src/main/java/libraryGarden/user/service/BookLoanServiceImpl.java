package libraryGarden.user.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookLoanMapper;
import libraryGarden.domain.LoanVo;
import libraryGarden.user.mapper.BookLoanMapper;

@Service
public class BookLoanServiceImpl implements BookLoanService{
	
    @Autowired
    private BookLoanMapper lm;
    
	@Autowired
    private AdminBookLoanMapper alm;

    @Override
    public String getUserLoanStatus(String userNumber) throws Exception {
		// 0) 당일 오전 연체 감지
    	alm.insertOverdueForPastDue();
    	
        return lm.selectUserLoanStatus(userNumber); // 대출 가능 여부 조회
    }

    @Override
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) throws Exception {
    	
        Map<String, Object> result = new HashMap<>();

        // 페이징 계산 추가
        int startPageNum = (page - 1) * perPageNum;

        List<Map<String, Object>> loanList = lm.selectUserLoanList(userNumber, startPageNum, perPageNum);

        int totalCount = lm.selectUserLoanTotalCount(userNumber);

        result.put("loanList", loanList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public String getUserName(String userNumber) throws Exception {
        return lm.selectUserName(userNumber); // 회원 이름 조회
    }
    
    
    @Override
    public boolean extendLoan(int lidx, String userNumber) throws Exception {
        // 1. 해당 대출 정보를 조회 (로그인한 사용자의 대출 정보여야 함)
        LoanVo lv = lm.selectLoanByIdAndUser(lidx, userNumber);
        if (lv == null) {
            // 해당 대출 기록이 없으면 연장 불가
            return false;
        }
        
        // 2. 현재 대출 상태가 '대출중'인지 확인 (대여중이어야 연장 가능)
        if (!"대출중".equals(lv.getStatus())) {
            return false;
        }
        
        // 이미 연장한 대출인지 확인
        if ("Y".equals(lv.getExtended())) {
            return false;
        }
        
        // 3. 현재 dueDate에서 7일 연장한 새 반납예정일 계산
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDueDate = dateFormat.parse(lv.getDueDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDueDate);
        cal.add(Calendar.DATE, 7);
        String newDueDate = dateFormat.format(cal.getTime());
        
        // 4. 해당 도서(lbidx)에 대해 가장 빠른 예약 픽업일 조회
        String earliestPickup = lm.selectEarliestReservationPickupDateByBook(lv.getLbidx());

        if (earliestPickup != null) {
            // 예약 픽업일과 새 dueDate를 Date 객체로 변환
            Date earliestPickupDate = dateFormat.parse(earliestPickup);
            Date newDueDateDate = dateFormat.parse(newDueDate);
            // 만약 연장 후 dueDate가 예약된 가장 빠른 픽업일과 같거나 이후라면 연장 불가능
            if (newDueDateDate.compareTo(earliestPickupDate) >= 0) {
                return false;
            }
        }
        
        // 5. 업데이트 쿼리 실행 (LOAN 테이블의 dueDate 업데이트)
        int updateCount = lm.extendLoan(lidx, newDueDate);
        return updateCount > 0;
    }

}
