package libraryGarden.admin.service;

import java.util.ArrayList;
import libraryGarden.domain.ReservationDto;
import libraryGarden.domain.SearchCriteria;

public interface AdminBookReservationService {
	
	public int bookReservationTotalCount(SearchCriteria scri, String filter); // 총 갯수
	public ArrayList<ReservationDto> bookReservationSelectAll(SearchCriteria scri, String filter); // 모든 리스트 가져오기

}
