package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.SearchCriteria;

public interface AdminDirectorApprovalService {
	
	int directorApprovalTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<ApprovalDto> directorApprovalSelectAll(SearchCriteria scri, String filter);

	public BookVo directorApprovalSelectOne(int aidx);

	public int directorApprovalDelete(int aidx);

	public int directorApprovalInsert(ApprovalVo av);
	
}
