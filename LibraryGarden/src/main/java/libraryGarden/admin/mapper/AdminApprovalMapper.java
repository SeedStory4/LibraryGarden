package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BookVo;

public interface AdminApprovalMapper {

	public int approvalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalDto> approvalSelectAll(HashMap<String,Object> hm);
	
	public BookVo approvalSelectOne(int aidx);

	public int approvalDelete(int aidx);
	
	public int approvalInsert(HashMap<String,Object> hm);
	
	public ApprovalVo approvalSelectAv(int aidx);
	
	public int approvalUpdate(HashMap<String,Object> hm);
}
