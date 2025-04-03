package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.ReservationDto;

public interface AdminBookReservationMapper {
	
	public int bookReservationTotalCount(HashMap<String,Object> hm);
	public ArrayList<ReservationDto> bookReservationSelectAll(HashMap<String,Object> hm);
	public int bookTotalCount(HashMap<String,Object> hm);
	public ArrayList<LibraryBookDto> bookSelectAll(HashMap<String,Object> hm);
	int checkOverdue(String userNumber);
	List<String> getReservedDatesByLbidx(int lbidx);
}
