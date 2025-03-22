package libraryGarden.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.admin.service.AdminBookLoanService;

@Controller
@RequestMapping("/admin/bookLoan")
public class AdminBookLoanController {
	
    @Autowired(required=false)
    private AdminBookLoanService adminBookLoanService;

	// 도서 대여
	@GetMapping("/bookLoanWrite.do")
	public String bookLoanWrite() {
		System.out.println("bookLoanWrite.do 호출됨");
		return "admin/bookLoan/bookLoanWrite";
	}
	
    // 회원번호로 대출 가능 여부와 대출 목록 조회 (AJAX)
	@GetMapping(value="/checkUserLoanStatus.do")
	@ResponseBody
	public Map<String, Object> checkUserLoanStatus(@RequestParam("userNumber") String userNumber) {
	    System.out.println("checkUserLoanStatus.do 호출됨: userNumber = " + userNumber);
	    Map<String, Object> result = adminBookLoanService.getUserLoanInfo(userNumber);
	    return result;
	}

}
