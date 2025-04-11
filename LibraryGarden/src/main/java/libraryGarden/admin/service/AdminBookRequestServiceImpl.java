package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookRequestMapper;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminBookRequestServiceImpl implements AdminBookRequestService{

	@Autowired
	private AdminBookRequestMapper brm;

	@Override
	public int bookRequestTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = brm.bookRequestTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<RequestDto> bookRequestSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<RequestDto> rlist = brm.bookRequestSelectAll(hm);
		
		return rlist;
	}

	@Override
	public BookVo bookRequestSelectOne(int rqidx) {

		BookVo bv = brm.bookRequestSelectOne(rqidx);
		
		return bv;
	};
	
	@Override
	public int statusUpdate(int rqidx, String status) {

		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("rqidx", rqidx);
		hm.put("status", status);
		
		int value = brm.statusUpdate(hm);
		
		return value;
		
	};

}
