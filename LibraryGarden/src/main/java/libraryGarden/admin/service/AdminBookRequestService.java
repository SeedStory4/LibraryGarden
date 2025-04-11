package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.BookVo;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

public interface AdminBookRequestService {
	
	int bookRequestTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<RequestDto> bookRequestSelectAll(SearchCriteria scri, String filter);

	public BookVo bookRequestSelectOne(int rqidx);

	public int statusUpdate(int rqidx, String status);
	
}
