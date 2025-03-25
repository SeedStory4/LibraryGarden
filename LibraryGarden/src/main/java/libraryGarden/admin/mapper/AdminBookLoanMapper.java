package libraryGarden.admin.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AdminBookLoanMapper {

    public String selectUserLoanStatus(String userNumber);
    public List<Map<String, Object>> selectUserLoanList(Map<String, Object> params);
    public int selectUserLoanTotalCount(String userNumber);
    public String selectUserName(String userNumber);
    public int updateOverdueStatus();
    
    public void insertBookLoan(@Param("userNumber") String userNumber, @Param("code") String code);
    public void updateLibraryBookStatusToLoan(@Param("code") String code);
    public String selectBookStatus(@Param("code") String code);
    public void deleteLoan(@Param("lidx") int lidx); // 대여 삭제
    public void updateLoanStatusToReturned(@Param("lidx") int lidx);
    public void updateLibraryBookStatusToAvailable(@Param("lidx") int lidx);
    public Date selectDueDate(@Param("lidx") int lidx);
    public void insertOverdue(@Param("lidx") int lidx, @Param("overduePenaltyDays") int overduePenaltyDays);

}
