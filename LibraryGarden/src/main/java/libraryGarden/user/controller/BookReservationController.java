package libraryGarden.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.ReservationDto;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.service.BookReservationService;

@Controller
@RequestMapping("/user/bookReservation")
public class BookReservationController {
	
	@Autowired(required=false)
	private BookReservationService adminBookReservationService;
	
	@Autowired(required=false)
	private PageMaker pm;
	

	// 도서예약 목록
	@RequestMapping(value="/bookReservationList.do")
	public String bookReservationList(
			SearchCriteria scri,
			ReservationDto rd,
			Model model) {
		 
		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 예약 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = rd.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = adminBookReservationService.bookReservationTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<ReservationDto> rlist = adminBookReservationService.bookReservationSelectAll(scri, filter);
		 
		 // "\" 등 검색시 오류 발생하지 않도록 검색어 encoding
		 UrlEncoder encoder = new UrlEncoder();		 
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("rlist", rlist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		return "admin/bookReservation/bookReservationList";
	}
	
	@ResponseBody
	@PostMapping("/cancelReservation.do")
	public Map<String, Object> cancelReservation(@RequestParam("ridx") int ridx) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        int updateCount = adminBookReservationService.cancelReservation(ridx);
	        if(updateCount > 0) {
	            result.put("success", true);
	            result.put("message", "예약이 취소되었습니다.");
	        } else {
	            result.put("success", false);
	            result.put("message", "해당 예약을 취소할 수 없습니다.");
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	        result.put("success", false);
	        result.put("message", "처리 중 오류가 발생했습니다.");
	    }
	    return result;
	}
	
	
	// 도서예약 등록
	@GetMapping("/bookReservationWrite.do")
	public String bookReservationWrite(
			@RequestParam(value = "userNumber", required = false) String userNumber,
			SearchCriteria scri,
			LibraryBookDto ld,
			Model model) {
		
		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 대출 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = ld.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = adminBookReservationService.bookTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<LibraryBookDto> lblist = adminBookReservationService.bookSelectAll(scri, filter);
		 
		 // "\" 등 검색시 오류 발생하지 않도록 검색어 encoding
		 UrlEncoder encoder = new UrlEncoder();		 
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("lblist", lblist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		 model.addAttribute("userNumber", userNumber); // JSP에서 필요
		 
		return "admin/bookReservation/bookReservationWrite";
	}
	
    // AJAX로 도서의 대출/예약 정보를 조회하여 예약불가 날짜 목록 반환
	@ResponseBody
	@GetMapping("/getReservedDates.do")
	public List<Map<String, String>> getReservedDates(
	        @RequestParam("lbidx") int lbidx,
	        @RequestParam("userNumber") String userNumber) {
	    return adminBookReservationService.getUnavailableDatesWithReasons(lbidx, userNumber);
	}
	
	
	@ResponseBody
	@PostMapping("/registerReservation.do")
	public Map<String, Object> registerReservation(
	        @RequestParam("lbidx") int lbidx,
	        @RequestParam("userNumber") String userNumber,
	        @RequestParam("pickupDate") String pickupDate) {
	    
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // 날짜 포맷 설정 (yyyy-MM-dd)
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        // 현재 날짜를 예약 신청일로 사용
	        Date regDate = new Date();
	        // 사용자가 선택한 픽업 날짜 (문자열로 받은 값을 Date 객체로 변환)
	        Date pickup = dateFormat.parse(pickupDate);
	        // 반납예정일은 픽업 날짜에서 6일 후로 계산
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(pickup);
	        cal.add(Calendar.DATE, 6);
	        Date dueDate = cal.getTime();
	        
	        // ReservationDto 객체 생성 및 값 설정
	        ReservationDto dto = new ReservationDto();
	        dto.setLbidx(lbidx);
	        dto.setUserNumber(userNumber);
	        dto.setRegDate(dateFormat.format(regDate));
	        dto.setPickupDate(pickupDate);  // "yyyy-MM-dd" 형식이어야 함.
	        dto.setDueDate(dateFormat.format(dueDate));
	        dto.setStatus("예약중");
	        
	        // 예약 등록 (INSERT)
	        int result = adminBookReservationService.registerReservation(dto);
	        if(result > 0) {
	            response.put("success", true);
	            response.put("message", "예약 등록에 성공했습니다.");
	        } else {
	            response.put("success", false);
	            response.put("message", "예약 등록에 실패했습니다.");
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("message", "예약 등록 중 예외가 발생했습니다.");
	    }
	    return response;
	}
	
	  // 수정 팝업에서 비활성화 날짜 조회
	  @ResponseBody
	  @GetMapping("/getReservedDatesForModify.do")
	  public List<Map<String, String>> getReservedDatesForModify(
	      @RequestParam int lbidx,
	      @RequestParam String userNumber,
	      @RequestParam int ridx) {
	    return adminBookReservationService
	             .getUnavailableDatesForModify(lbidx, userNumber, ridx);
	  }

	  // 실제 예약 수정 처리
	  @ResponseBody
	  @PostMapping("/modifyReservation.do")
	  public Map<String, Object> modifyReservation(
	      @RequestParam int ridx,
	      @RequestParam String pickupDate) {
	    Map<String,Object> resp = new HashMap<>();
	    try {
	      SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	      Date pick = fmt.parse(pickupDate);
	      Calendar cal = Calendar.getInstance(); cal.setTime(pick);
	      cal.add(Calendar.DATE, 6);
	      String due = fmt.format(cal.getTime());

	      ReservationDto dto = new ReservationDto();
	      dto.setRidx(ridx);
	      dto.setPickupDate(pickupDate);
	      dto.setDueDate(due);

	      int updated = adminBookReservationService.updateReservation(dto);
	      if (updated>0) {
	        resp.put("success", true);
	        resp.put("message", "예약이 성공적으로 수정되었습니다.");
	      } else {
	        resp.put("success", false);
	        resp.put("message", "예약 수정에 실패했습니다.");
	      }
	    } catch(Exception e) {
	      e.printStackTrace();
	      resp.put("success", false);
	      resp.put("message", "서버 오류가 발생했습니다.");
	    }
	    return resp;
	  }
	  
	  //예약 등록 회원 존재 여부 체크
	  @ResponseBody
	  @GetMapping("/checkUser.do")
	  public Map<String, Object> checkUser(@RequestParam String userNumber) {
	      Map<String, Object> resp = new HashMap<>();
	      // adminBookReservationService 쪽으로 위 count를 호출
	      boolean exists = adminBookReservationService.countUserByNumber(userNumber) > 0;
	      resp.put("exists", exists);
	      if (!exists) {
	        resp.put("message", "회원번호가 존재하지 않습니다.");
	      }
	      return resp;
	  }


}
