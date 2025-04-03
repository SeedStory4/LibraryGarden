package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookRequestMapper;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminBookRequestServiceImpl implements AdminBookRequestService{

	@Autowired
	private AdminBookRequestMapper brm;

	@Override
	public int bookRequestTotalCount(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		int cnt = brm.bookRequestTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<RequestDto> bookRequestSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		ArrayList<RequestDto> rlist = brm.bookRequestSelectAll(hm);
		
		return rlist;
	}
	
}
