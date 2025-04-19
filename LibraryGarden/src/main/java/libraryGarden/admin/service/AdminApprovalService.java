package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.SearchCriteria;

public interface AdminApprovalService {
	
	int approvalTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<ApprovalDto> approvalSelectAll(SearchCriteria scri, String filter);
	
	public BookVo approvalSelectOne(int aidx);
	
	public int approvalDelete(ApprovalVo av, String status) throws Exception;

	public int approvalInsert(ApprovalVo av, BookVo bv, String status) throws Exception;

	public ApprovalVo approvalSelectAv(int aidx);

	public int approvalUpdate(ApprovalVo av, BookVo bv) throws Exception;
	
	public int approvalProcessing(ApprovalVo av);
	
}
