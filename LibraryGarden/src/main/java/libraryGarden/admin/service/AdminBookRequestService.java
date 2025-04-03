package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

public interface AdminBookRequestService {
	
	int bookRequestTotalCount(SearchCriteria scri);
	
	public ArrayList<RequestDto> bookRequestSelectAll(SearchCriteria scri);
	
}
