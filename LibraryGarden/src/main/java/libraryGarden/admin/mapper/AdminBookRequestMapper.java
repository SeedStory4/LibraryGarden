package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.BookVo;
import libraryGarden.domain.RequestDto;

public interface AdminBookRequestMapper {

	public int bookRequestTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<RequestDto> bookRequestSelectAll(HashMap<String,Object> hm);

	public BookVo bookRequestSelectOne(int reqidx);
	
}
