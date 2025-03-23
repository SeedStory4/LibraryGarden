package libraryGarden.admin.mapper;

import java.util.List;
import java.util.Map;

public interface AdminBookLoanMapper {
	
    public String selectUserLoanStatus(String userNumber);
    public List<Map<String, Object>> selectUserLoanList(String userNumber);
    public String selectUserName(String userNumber);

}
