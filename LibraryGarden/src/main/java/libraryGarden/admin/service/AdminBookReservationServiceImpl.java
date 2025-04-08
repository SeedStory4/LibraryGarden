package libraryGarden.admin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import libraryGarden.admin.mapper.AdminBookReservationMapper;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.LoanVo;
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
	
	@Override
	public int bookTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = rm.bookTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<LibraryBookDto> bookSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<LibraryBookDto> lblist = rm.bookSelectAll(hm);
		
		return lblist;
	}
	
	
	@Override
	public List<Map<String, String>> getUnavailableDatesWithReasons(int lbidx, String userNumber) {
	    Set<String> reservationDates = new HashSet<>();
	    Set<String> overdueDates = new HashSet<>();
	    SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd");

	    // 예약 날짜
	    List<ReservationDto> reservations = rm.getReservationsByBook(lbidx);
	    for (ReservationDto res : reservations) {
	        try {
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(dbFormat.parse(res.getPickupDate()));
	            for (int i = 0; i < 7; i++) {
	                reservationDates.add(outputFormat.format(cal.getTime()));
	                cal.add(Calendar.DATE, 1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // 대출 날짜
	    List<LoanVo> loans = rm.getLoansByBook(lbidx);
	    for (LoanVo loan : loans) {
	        try {
	            Calendar cal = Calendar.getInstance();
	            Date start = dbFormat.parse(loan.getLoanDate());
	            Date end = dbFormat.parse(loan.getDueDate());
	            cal.setTime(start);
	            while (!cal.getTime().after(end)) {
	                reservationDates.add(outputFormat.format(cal.getTime()));
	                cal.add(Calendar.DATE, 1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // 연체 날짜
	    List<HashMap<String, String>> overdues = rm.getOverduePeriodsByUser(userNumber);
	    for (HashMap<String, String> od : overdues) {
	        String start = od.get("startDate");
	        String end = od.get("endDate");
	        try {
	            Calendar cal = Calendar.getInstance();
	            Date startDate = dbFormat.parse(start);
	            Date endDate = dbFormat.parse(end);
	            cal.setTime(startDate);
	            while (cal.getTime().before(endDate)) { // 전날까지
	                overdueDates.add(outputFormat.format(cal.getTime()));
	                cal.add(Calendar.DATE, 1);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // 날짜 + 사유 분리해서 보내기
	    List<Map<String, String>> result = new ArrayList<>();
	    for (String date : reservationDates) {
	        result.add(Map.of("date", date, "reason", "예약"));
	    }
	    for (String date : overdueDates) {
	        result.add(Map.of("date", date, "reason", "연체"));
	    }

	    return result;
	}


}
