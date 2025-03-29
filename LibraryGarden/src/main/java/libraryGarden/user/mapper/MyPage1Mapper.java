package libraryGarden.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MyPage1Mapper {
	
    String selectUserLoanStatus(String userNumber); // 대출 가능 여부 조회
    List<Map<String, Object>> selectUserLoanList(@Param("userNumber") String userNumber, @Param("startPageNum") int startPageNum, @Param("perPageNum") int perPageNum); // 대출 목록 조회
    int selectUserLoanTotalCount(String userNumber); // 대출 목록 전체 개수 조회
    String selectUserName(String userNumber); // 회원 이름 조회
    List<Map<String, Object>> selectUserReservationList(@Param("userNumber") String userNumber, @Param("startPageNum") int startPageNum, @Param("perPageNum") int perPageNum); // 예약 목록 조회
    int selectUserReservationTotalCount(String userNumber); // 예약 목록 전체 개수 조회

}
