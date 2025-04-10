package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.BookVo;

public interface AdminDirectorApprovalMapper {

	public int directorApprovalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalDto> directorApprovalSelectAll(HashMap<String,Object> hm);

	public BookVo directorApprovalSelectOne(int aidx);

	public int directorApprovalDelete(int aidx);
	
	public int directorApprovalInsert(HashMap<String,Object> hm);
	
}
