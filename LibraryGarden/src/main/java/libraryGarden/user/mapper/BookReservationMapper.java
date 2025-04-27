package libraryGarden.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.LoanVo;
import libraryGarden.domain.ReservationDto;

public interface BookReservationMapper {
	
	public int bookReservationTotalCount(HashMap<String,Object> hm);
	public ArrayList<ReservationDto> bookReservationSelectAll(HashMap<String,Object> hm);
	public int bookTotalCount(HashMap<String,Object> hm);
	public ArrayList<LibraryBooksDto> bookSelectAll(HashMap<String,Object> hm);
	public List<HashMap<String, String>> getOverduePeriodsByUser(String userNumber); // 예약 연체 조회
    // 해당 도서(lbidx)의 예약 pickupDate 조회
    public List<ReservationDto> getReservationsByBook(int lbidx);
    // 해당 도서(lbidx)의 대출(loanDate, dueDate) 정보 조회 (LoanVo 사용)
    public List<LoanVo> getLoansByBook(int lbidx);
    // 예약 등록
    public int insertReservation(ReservationDto reservation);
    // 픽업일이 7일 남은 도서들의 lbidx 찾아서 LIBRARYBOOKS 상태 예약대기 업데이트
    public int updateBooksToWaitStatus();
    public int cancelReservation(int ridx); // 예약삭제
    // 예약 취소된 예약의 lbidx 조회
    Integer findLbidxByReservation(int ridx);
    // 해당 도서(lbidx)에 대해 오늘 기준으로 반납예정일이 미래인 대출 건 수 조회
    int getActiveLoanCountByDueDate(int lbidx);
    // 도서 상태 업데이트: LIBRARYBOOKS 테이블에서 lbidx로 상태 변경
    int updateBookStatus(@Param("lbidx") int lbidx, @Param("status") String status);
    // 내 예약(ridx)만 제외하고 해당 도서(lbidx)의 예약만 조회 
    List<ReservationDto> getReservationsForModify(@Param("lbidx") int lbidx,@Param("ridx") int ridx);
    // 실제 예약 수정(UPDATE) 
    int updateReservation(ReservationDto reservation);
    //회원번호로 USER 테이블에 몇 건이 있는지 조회
    int countUserByNumber(String userNumber);
    List<Map<String, Object>> selectUserReservationList(@Param("userNumber") String userNumber, @Param("startPageNum") int startPageNum, @Param("perPageNum") int perPageNum); // 내도서 예약 목록 조회
    int selectUserReservationTotalCount(String userNumber); // 내 도서 예약 목록 전체 개수 조회
    int countActiveReservationByUserAndBook(Map<String, Object> params);
    /** 특정 회원이 특정 도서를 반납하지 않고 대출 중인지 카운트 */
    int countActiveLoanByUserAndBook(Map<String,Object> params);
}
