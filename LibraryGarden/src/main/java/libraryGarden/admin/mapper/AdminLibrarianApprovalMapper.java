package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.ApprovalVo;

public interface AdminLibrarianApprovalMapper {

	public int librarianApprovalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalVo> librarianApprovalSelectAll(HashMap<String,Object> hm);
	
}
