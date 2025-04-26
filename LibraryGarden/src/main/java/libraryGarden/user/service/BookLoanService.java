package libraryGarden.user.service;

import java.util.Map;

public interface BookLoanService {
	
    public String getUserLoanStatus(String userNumber) throws Exception; // 대출 가능 여부
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) throws Exception; // 대출 목록 조회
    public String getUserName(String userNumber) throws Exception; // 회원 이름 조회
    // 대출 연장 처리 메서드: loanId와 userNumber를 받아 연장 가능 여부를 반환 (true = 연장 성공)
    public boolean extendLoan(int lidx, String userNumber) throws Exception;

}
