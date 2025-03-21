package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/bookLoan")
public class BookLoanController {

	// 도서 대여
	@GetMapping("/bookLoanWrite.do")
	public String bookLoanWrite() {
		return "admin/bookLoan/bookLoanWrite";
	}

}
