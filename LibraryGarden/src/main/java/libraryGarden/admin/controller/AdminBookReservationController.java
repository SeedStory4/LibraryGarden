package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.admin.service.AdminBookReservationService;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.ReservationDto;
import libraryGarden.domain.SearchCriteria;

@Controller
@RequestMapping("/admin/bookReservation")
public class AdminBookReservationController {
	
	@Autowired(required=false)
	private AdminBookReservationService adminBookReservationService;
	
	@Autowired(required=false)
	private PageMaker pm;
	

	// 도서예약 목록
	@RequestMapping(value="/bookReservationList.do")
	public String bookReservationList(
			SearchCriteria scri,
			ReservationDto rd,
			Model model) {
		 
		 // "\" 등 검색시 오류 발생하지 않도록 검색어 encoding
		 UrlEncoder encoder = new UrlEncoder();		 
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 예약 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = rd.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = adminBookReservationService.bookReservationTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<ReservationDto> rlist = adminBookReservationService.bookReservationSelectAll(scri, filter);
		 
		 model.addAttribute("rlist", rlist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		return "admin/bookReservation/bookReservationList";
	}
	
	
	// 도서예약 등록
	@GetMapping("/bookReservationWrite.do")
	public String bookReservationWrite() {
		return "admin/bookReservation/bookReservationWrite";
	}
	
	// 도서예약 수정 팝업
	@GetMapping("/popBookReservationModify.do")
	public String popBookReservationModify() {
		return "admin/bookReservation/popBookReservationModify";
	}
	
	// 도서 예약 팝업
	@GetMapping("/popBookReservationWrite.do")
	public String popBookReservationWrite() {
		return "admin/bookReservation/popBookReservationWrite";
	}

}
