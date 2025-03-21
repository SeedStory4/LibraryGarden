package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/myPage")
public class MyPage1Controller {
	
	
	// 내 도서 대출이력관리
	@GetMapping("/myPageLoanList.do")
	public String myPageLoanList() {
		return "user/myPage/myPageLoanList";
	}
	
	// 내 도서 예약관리
	@GetMapping("/myPageReservationList.do")
	public String myPageReservationList() {
		return "user/myPage/myPageReservationList";
	}

}
