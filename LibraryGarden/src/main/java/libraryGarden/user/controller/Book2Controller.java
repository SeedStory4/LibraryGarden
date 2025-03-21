package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/book")
public class Book2Controller {

	// 도서 예약
	@GetMapping("/popBookReservationWrite.do")
	public String popBookReservationWrite() {
		return "user/book/popBookReservationWrite";
	}
}