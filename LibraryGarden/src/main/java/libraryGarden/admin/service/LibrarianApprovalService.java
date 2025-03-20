package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.SearchCriteria;

public interface LibrarianApprovalService {
	
	int librarianApprovalTotalCount(SearchCriteria scri);
	
	public ArrayList<ApprovalVo> librarianApprovalSelectAll(SearchCriteria scri);
	
}
