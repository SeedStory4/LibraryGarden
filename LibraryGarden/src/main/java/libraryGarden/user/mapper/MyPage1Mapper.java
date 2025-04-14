package libraryGarden.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import libraryGarden.domain.LoanVo;

public interface MyPage1Mapper {
	
    String selectUserLoanStatus(String userNumber); // 대출 가능 여부 조회
    List<Map<String, Object>> selectUserLoanList(@Param("userNumber") String userNumber, @Param("startPageNum") int startPageNum, @Param("perPageNum") int perPageNum); // 대출 목록 조회
    int selectUserLoanTotalCount(String userNumber); // 대출 목록 전체 개수 조회
    String selectUserName(String userNumber); // 회원 이름 조회
    List<Map<String, Object>> selectUserReservationList(@Param("userNumber") String userNumber, @Param("startPageNum") int startPageNum, @Param("perPageNum") int perPageNum); // 예약 목록 조회
    int selectUserReservationTotalCount(String userNumber); // 예약 목록 전체 개수 조회
    // 대출 정보를 loanId와 userNumber로 조회
    LoanVo selectLoanByIdAndUser(@Param("lidx") int lidx, @Param("userNumber") String userNumber);
    // 해당 도서(lbidx)의 예약(예약대기) 중 가장 빠른 픽업일 조회
    String selectEarliestReservationPickupDateByBook(@Param("lbidx") int lbidx);
    // 대출 연장: LOAN 테이블의 dueDate 업데이트
    int extendLoan(@Param("lidx") int lidx, @Param("newDueDate") String newDueDate);

}
