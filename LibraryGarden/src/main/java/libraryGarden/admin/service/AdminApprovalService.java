package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.SearchCriteria;

public interface AdminApprovalService {
	
	int getApprovalTotalCount(SearchCriteria scri, String filter);
	
	public ArrayList<ApprovalDto> getApprovalSelectAll(SearchCriteria scri, String filter);
	
	public BooksVo getApprovalSelectOne(int aidx);
	
	public int deleteApproval(ApprovalVo av, String status) throws Exception;

	public int insertApproval(ApprovalVo av, BooksVo bv, String status) throws Exception;

	public ApprovalVo getApprovalSelectAv(int aidx);

	public int updateApproval(ApprovalVo av, BooksVo bv) throws Exception;
	
	public int updateApprovalProcessing(ApprovalVo av);
	
}
