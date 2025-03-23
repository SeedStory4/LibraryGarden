package libraryGarden.admin.mapper;

import java.util.List;
import java.util.Map;

public interface AdminBookLoanMapper {

    public String selectUserLoanStatus(String userNumber);
    public List<Map<String, Object>> selectUserLoanList(Map<String, Object> params);
    public int selectUserLoanTotalCount(String userNumber);
    public String selectUserName(String userNumber);
    public int updateOverdueStatus();

}
