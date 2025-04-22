package libraryGarden.admin.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
	public int cancelReservation(int ridx) {
	    // 1) 예약 레코드만 취소 표시
	    int updatedCount = rm.cancelReservation(ridx);
	    if (updatedCount > 0) {
	        // 2) 취소된 예약의 도서 번호(lbidx) 조회
	        Integer lbidx = rm.findLbidxByReservation(ridx);
	        if (lbidx != null) {
	            // 3) 아직 반납되지 않은 대출이 있는지 확인
	            int activeLoanCount = rm.getActiveLoanCountByDueDate(lbidx);
	            // 4) 대출중이면 "대출중", 아니면 "대출가능"으로 복원
	            String newStatus = (activeLoanCount > 0) ? "대출중" : "대출가능";
	            rm.updateBookStatus(lbidx, newStatus);
	        }
	    }
	    return updatedCount;
	}
	
	
	@Override
	public List<Map<String, String>> getUnavailableDatesWithReasons(int lbidx, String userNumber) {
	    Set<String> regDates = new HashSet<>();
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
	            	regDates.add(outputFormat.format(cal.getTime()));
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
	            	regDates.add(outputFormat.format(cal.getTime()));
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
	    for (String date : regDates) {
	        result.add(Map.of("date", date, "reason", "예약"));
	    }
	    for (String date : overdueDates) {
	        result.add(Map.of("date", date, "reason", "연체"));
	    }

	    return result;
	}
	
	@Override
	public int registerReservation(ReservationDto reservation) {
	    int inserted = rm.insertReservation(reservation);

	    // → 전역 호출 제거 rm.updateBooksToWaitStatus();

	    // 대신 방금 INSERT한 이 책만, 
	    //  오늘부터 픽업일이 0~6일 이내면 바로 상태 바꿔 주기
	    long days = ChronoUnit.DAYS.between(
	        LocalDate.now(), 
	        LocalDate.parse(reservation.getPickupDate())
	    );
	    if (days >= 0 && days <= 6) {
	        rm.updateBookStatus(reservation.getLbidx(), "예약대기");
	    }
	    return inserted;
	}
    
    @Override
    public List<Map<String, String>> getUnavailableDatesForModify(int lbidx, String userNumber, int ridx) {
        // “수정”용: 내 예약(ridx)만 제외하고 동일 로직
        Set<String> regDates = new HashSet<>();
        Set<String> overdueDates = new HashSet<>();
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd");

        // 내 예약을 제외한 모든 예약 pickupDate (7일간)
        for (ReservationDto res : rm.getReservationsForModify(lbidx, ridx)) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dbFormat.parse(res.getPickupDate()));
                for (int i = 0; i < 7; i++) {
                	regDates.add(outputFormat.format(cal.getTime()));
                    cal.add(Calendar.DATE, 1);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // 대출 날짜 (기존과 동일)
        for (LoanVo loan : rm.getLoansByBook(lbidx)) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dbFormat.parse(loan.getLoanDate()));
                Date end = dbFormat.parse(loan.getDueDate());
                while (!cal.getTime().after(end)) {
                	regDates.add(outputFormat.format(cal.getTime()));
                    cal.add(Calendar.DATE, 1);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        // 연체 기간 (내 연체 포함)
        for (Map<String, String> od : rm.getOverduePeriodsByUser(userNumber)) {
            try {
                Calendar cal = Calendar.getInstance();
                Date start = dbFormat.parse(od.get("startDate"));
                Date end   = dbFormat.parse(od.get("endDate"));
                cal.setTime(start);
                while (cal.getTime().before(end)) {
                    overdueDates.add(outputFormat.format(cal.getTime()));
                    cal.add(Calendar.DATE, 1);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        List<Map<String, String>> result = new ArrayList<>();
        regDates.forEach(d -> result.add(Map.of("date", d, "reason", "예약")));
        overdueDates    .forEach(d -> result.add(Map.of("date", d, "reason", "연체")));
        return result;
    }
    
    @Override
    public int updateReservation(ReservationDto reservation) {
        return rm.updateReservation(reservation);
    }
    
    @Override
    public int countUserByNumber(String userNumber) {
        return rm.countUserByNumber(userNumber);
    }



}
