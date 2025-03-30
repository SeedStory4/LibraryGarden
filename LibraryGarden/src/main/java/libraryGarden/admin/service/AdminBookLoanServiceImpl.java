package libraryGarden.admin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookLoanMapper;

@Service
public class AdminBookLoanServiceImpl implements AdminBookLoanService{
	
    @Autowired
    private AdminBookLoanMapper adminBookLoanMapper;
    
    
    @Override
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) {
        Map<String, Object> result = new HashMap<>();
        //System.out.println("서비스 호출됨: userNumber = " + userNumber);

        String userName = adminBookLoanMapper.selectUserName(userNumber);
        //System.out.println("회원 이름 조회 결과: " + userName);
        result.put("userName", userName);

        String loanStatus = adminBookLoanMapper.selectUserLoanStatus(userNumber);
        //System.out.println("대출 가능 여부 조회 결과: " + loanStatus);
        result.put("loanStatus", loanStatus);

        Map<String, Object> params = new HashMap<>();
        int startPageNum = (page - 1) * perPageNum;
        params.put("userNumber", userNumber);
        params.put("startPageNum", startPageNum);
        params.put("perPageNum", perPageNum);

        List<Map<String, Object>> loanList = adminBookLoanMapper.selectUserLoanList(params);
        int totalCount = adminBookLoanMapper.selectUserLoanTotalCount(userNumber);
        
        result.put("loanList", loanList);
        result.put("totalCount", totalCount);

        return result;
    }
    
    @Override
    public void addBookLoan(String userNumber, String code) throws Exception {
        // 대여 정보 등록
        adminBookLoanMapper.insertBookLoan(userNumber, code);
        // 도서 상태 업데이트 (대출중으로)
        adminBookLoanMapper.updateLibraryBookStatusToLoan(code);
    }
    
    @Override
    public String getBookStatus(String code) throws Exception {
        String status = adminBookLoanMapper.selectBookStatus(code);
        if (status == null) {
            return "없는 도서";  // 도서 코드가 존재하지 않는 경우
        }
        return status;
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
        Date dueDate = adminBookLoanMapper.selectDueDate(lidx);
        Date now = new Date();

        // 날짜만 비교하도록 처리
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String dueDateStr = sdf.format(dueDate);
        String nowStr = sdf.format(now);

        String status;
        if (nowStr.compareTo(dueDateStr) > 0) {
            // 연체인 경우
            long overdueDays = (now.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
            int overduePenaltyDays = (int) overdueDays * 2 + 1;

            adminBookLoanMapper.insertOverdue(lidx, overduePenaltyDays);
            status = "연체반납";
        } else {
            status = "반납완료";
        }

        adminBookLoanMapper.updateLoanStatusToReturned(lidx, status);
        adminBookLoanMapper.updateLibraryBookStatusToAvailable(lidx);
    }

}
