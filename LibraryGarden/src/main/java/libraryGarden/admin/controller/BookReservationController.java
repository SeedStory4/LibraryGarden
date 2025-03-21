package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/bookReservation")
public class BookReservationController {
	
	// 도서예약 목록
	@GetMapping("/bookReservationList.do")
	public String bookReservationList() {
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
