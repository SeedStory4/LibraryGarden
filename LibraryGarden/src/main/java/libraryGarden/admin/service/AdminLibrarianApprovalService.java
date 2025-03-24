package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.LibraryBookVO;
import libraryGarden.domain.SearchCriteria;

public interface AdminLibrarianApprovalService {
	
	int librarianApprovalTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<ApprovalDto> librarianApprovalSelectAll(SearchCriteria scri, String filter);
	
	public LibraryBookVO(int aidx);
	
}
