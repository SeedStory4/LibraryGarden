package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import libraryGarden.domain.ReservationDto;

public interface AdminBookReservationMapper {
	
	public int bookReservationTotalCount(HashMap<String,Object> hm);
	public ArrayList<ReservationDto> bookReservationSelectAll(HashMap<String,Object> hm);

}
