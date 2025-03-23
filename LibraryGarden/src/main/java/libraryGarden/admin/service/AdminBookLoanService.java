package libraryGarden.admin.service;

import java.util.Map;

public interface AdminBookLoanService {

	public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum);	
	public void addBookLoan(String userNumber, String code) throws Exception;
    public String getBookStatus(String code) throws Exception;
    public boolean isUserOverdue(String userNumber) throws Exception;
    public void deleteLoan(int lidx) throws Exception; // 도서 삭제
    public void returnBookLoan(int lidx) throws Exception; // 도서 반납

}
