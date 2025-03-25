package libraryGarden.user.service;

import java.util.Map;

public interface MyPage1Service {
	
    public String getUserLoanStatus(String userNumber) throws Exception; // 대출 가능 여부
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) throws Exception; // 대출 목록 조회
    public String getUserName(String userNumber) throws Exception; // 회원 이름 조회

}
