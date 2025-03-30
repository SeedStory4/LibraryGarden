package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.ReservationDto;
import libraryGarden.domain.SearchCriteria;

public interface AdminBookReservationService {
	
	public int bookReservationTotalCount(SearchCriteria scri, String filter); // 예약 총 갯수
	public ArrayList<ReservationDto> bookReservationSelectAll(SearchCriteria scri, String filter); // 예약 모든 리스트 가져오기
	public int bookTotalCount(SearchCriteria scri, String filter); // 책 총 갯수
	public ArrayList<LibraryBookDto> bookSelectAll(SearchCriteria scri, String filter); // 책 모든 리스트 가져오기
	public boolean hasOverdue(String userNumber);
}
