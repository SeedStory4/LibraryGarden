package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.SearchCriteria;

public interface AdminDirectorApprovalService {
	
	int directorApprovalTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<ApprovalDto> directorApprovalSelectAll(SearchCriteria scri, String filter);
	
}
