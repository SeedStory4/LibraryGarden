package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.SearchCriteria;

public interface AdminApprovalService {
	
	int approvalTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<ApprovalDto> approvalSelectAll(SearchCriteria scri, String filter);
	
	public BooksVo approvalSelectOne(int aidx);
	
	public int approvalDelete(ApprovalVo av, String status) throws Exception;

	public int approvalInsert(ApprovalVo av, BooksVo bv, String status) throws Exception;

	public ApprovalVo approvalSelectAv(int aidx);

	public int approvalUpdate(ApprovalVo av, BooksVo bv) throws Exception;
	
	public int approvalProcessing(ApprovalVo av);
	
}
