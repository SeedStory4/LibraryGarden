package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import libraryGarden.admin.mapper.AdminBookReservationMapper;
import libraryGarden.domain.ReservationDto;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminBookReservationServiceImpl implements AdminBookReservationService{
	
	@Autowired
	private AdminBookReservationMapper rm;
	
	
	@Override
	public int bookReservationTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = rm.bookReservationTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<ReservationDto> bookReservationSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<ReservationDto> rlist = rm.bookReservationSelectAll(hm);
		
		return rlist;
	}

}
