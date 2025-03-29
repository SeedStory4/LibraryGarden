package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.BookVo;

public interface AdminLibrarianApprovalMapper {

	public int librarianApprovalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalDto> librarianApprovalSelectAll(HashMap<String,Object> hm);
	
	public BookVo librarianApprovalSelectOne(int aidx);
	
}
