package libraryGarden.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.ReservationDto;
import libraryGarden.domain.SearchCriteria;

public interface BookReservationService {
	
	public int bookReservationTotalCount(SearchCriteria scri, String filter); // 예약 총 갯수
	public ArrayList<ReservationDto> bookReservationSelectAll(SearchCriteria scri, String filter); // 예약 모든 리스트 가져오기
	public int bookTotalCount(SearchCriteria scri, String filter); // 책 총 갯수
	public ArrayList<LibraryBooksDto> bookSelectAll(SearchCriteria scri, String filter); // 책 모든 리스트 가져오기
    // 신규 추가 메소드: 해당 도서(lbidx)의 예약/대출 정보를 기반으로 예약 불가능한 날짜 목록 반환
    public List<Map<String, String>> getUnavailableDatesWithReasons(int lbidx, String userNumber);
    // 예약 등록 메소드 추가
    public int registerReservation(ReservationDto reservation);
    public int cancelReservation(int ridx); // 예약 삭제
    // 수정 팝업에서 불가능 날짜 조회
    List<Map<String,String>> getUnavailableDatesForModify(int lbidx, String userNumber, int ridx);
    // 실제 수정
    int updateReservation(ReservationDto reservation);
    int countUserByNumber(String userNumber);
    public Map<String, Object> getUserReservationInfo(String userNumber, int page, int perPageNum) throws Exception; // 내도서 예약 목록 조회
    boolean hasActiveReservation(int lbidx, String userNumber);

    

}
