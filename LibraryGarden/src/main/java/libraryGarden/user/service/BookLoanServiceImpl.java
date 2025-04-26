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
    private BookLoanMapper bookLoanMapper;
    
	@Autowired
    private AdminBookLoanMapper adminBookLoanMapper;

    @Override
    public String getUserLoanStatus(String userNumber) throws Exception {
		// 0) 당일 오전 연체 감지
	    adminBookLoanMapper.insertOverdueForPastDue();
    	
        return bookLoanMapper.selectUserLoanStatus(userNumber); // 대출 가능 여부 조회
    }

    @Override
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) throws Exception {
    	
        Map<String, Object> result = new HashMap<>();

        // 페이징 계산 추가
        int startPageNum = (page - 1) * perPageNum;

        List<Map<String, Object>> loanList = bookLoanMapper.selectUserLoanList(userNumber, startPageNum, perPageNum);

        int totalCount = bookLoanMapper.selectUserLoanTotalCount(userNumber);

        result.put("loanList", loanList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public String getUserName(String userNumber) throws Exception {
        return bookLoanMapper.selectUserName(userNumber); // 회원 이름 조회
    }
    
    
    @Override
    public boolean extendLoan(int lidx, String userNumber) throws Exception {
        // 1. 해당 대출 정보를 조회 (로그인한 사용자의 대출 정보여야 함)
        LoanVo loan = bookLoanMapper.selectLoanByIdAndUser(lidx, userNumber);
        if (loan == null) {
            // 해당 대출 기록이 없으면 연장 불가
            System.out.println("대출 기록이 없습니다.");
            return false;
        }
        
        // 2. 현재 대출 상태가 '대출중'인지 확인 (대여중이어야 연장 가능)
        if (!"대출중".equals(loan.getStatus())) {
            System.out.println("대출중 상태가 아니므로 연장 불가능: " + loan.getStatus());
            return false;
        }
        
        // 이미 연장한 대출인지 확인
        if ("Y".equals(loan.getExtended())) {
            System.out.println("이미 연장한 대출입니다.");
            return false;
        }
        
        // 3. 현재 dueDate에서 7일 연장한 새 반납예정일 계산
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDueDate = dateFormat.parse(loan.getDueDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDueDate);
        cal.add(Calendar.DATE, 7);
        String newDueDate = dateFormat.format(cal.getTime());
        
        // 4. 해당 도서(lbidx)에 대해 가장 빠른 예약 픽업일 조회
        String earliestPickup = bookLoanMapper.selectEarliestReservationPickupDateByBook(loan.getLbidx());
        System.out.println("loan.getLbidx(): " + loan.getLbidx());
        System.out.println("Computed newDueDate: " + newDueDate);
        System.out.println("Retrieved earliestPickup: " + earliestPickup);
        
        if (earliestPickup != null) {
            // 예약 픽업일과 새 dueDate를 Date 객체로 변환
            Date earliestPickupDate = dateFormat.parse(earliestPickup);
            Date newDueDateDate = dateFormat.parse(newDueDate);
            System.out.println("비교: newDueDateDate " + newDueDateDate + " vs earliestPickupDate " + earliestPickupDate);
            // 만약 연장 후 dueDate가 예약된 가장 빠른 픽업일과 같거나 이후라면 연장 불가능
            if (newDueDateDate.compareTo(earliestPickupDate) >= 0) {
                System.out.println("연장 불가 조건 만족: 연장 후 dueDate가 예약 픽업일보다 크거나 같음");
                return false;
            }
        }
        
        // 5. 업데이트 쿼리 실행 (LOAN 테이블의 dueDate 업데이트)
        int updateCount = bookLoanMapper.extendLoan(lidx, newDueDate);
        System.out.println("연장 업데이트 결과: " + updateCount);
        return updateCount > 0;
    }

}
