package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BooksVo;

public interface AdminApprovalMapper {

	public int getApprovalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalDto> getApprovalSelectAll(HashMap<String,Object> hm);
	
	public BooksVo getApprovalSelectOne(int aidx);

	public int deleteApproval(int aidx);
	
	public int insertApproval(HashMap<String,Object> hm);
	
	public ApprovalVo getApprovalSelectAv(int aidx);
	
	public int updateApproval(HashMap<String,Object> hm);

	public int updateApprovalProcessing(ApprovalVo av);
	
}
