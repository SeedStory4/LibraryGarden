package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminDirectorApprovalMapper;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminDirectorApprovalServiceImpl implements AdminDirectorApprovalService{

	@Autowired
	private AdminDirectorApprovalMapper dm;

	@Override
	public int directorApprovalTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = dm.directorApprovalTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<ApprovalDto> directorApprovalSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<ApprovalDto> alist = dm.directorApprovalSelectAll(hm);
		
		return alist;
	}
	
	@Override
	public BookVo directorApprovalSelectOne(int aidx){
		
		BookVo bv = dm.directorApprovalSelectOne(aidx);
		
		return bv;	
	}
}
