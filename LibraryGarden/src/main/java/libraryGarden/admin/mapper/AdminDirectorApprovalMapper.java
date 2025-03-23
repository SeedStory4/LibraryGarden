package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.ApprovalDto;

public interface AdminDirectorApprovalMapper {

	public int directorApprovalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalDto> directorApprovalSelectAll(HashMap<String,Object> hm);
	
}
