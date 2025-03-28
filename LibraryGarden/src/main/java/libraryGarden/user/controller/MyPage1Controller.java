package libraryGarden.user.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import libraryGarden.domain.PageMaker;
import libraryGarden.domain.UserVo;
import libraryGarden.user.service.MyPage1Service;

@Controller
@RequestMapping("/user/myPage")
public class MyPage1Controller {
	
    @Autowired
    private MyPage1Service myPage1Service;
    
	@Autowired(required=false)
	private PageMaker pm;
	
	
    // 내 도서 대출 목록 화면
	@GetMapping("/myPageLoanList.do")
	public String myPageLoanList(
	    @RequestParam(value = "page", defaultValue = "1") int page,
	    HttpSession session,
	    Model model
	) throws Exception {
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/user/user/userLogin.do";
	    }

	    String userNumber = loginUser.getUserNumber();
	    String name = loginUser.getName();

	    String loanStatus = myPage1Service.getUserLoanStatus(userNumber);
	    model.addAttribute("loanStatus", loanStatus);

	    int perPageNum = 12;
	    Map<String, Object> loanList = myPage1Service.getUserLoanInfo(userNumber, page, perPageNum);
	    model.addAttribute("loanList", loanList);

	    int totalCount = (int) loanList.get("totalCount");
	    int totalPageCount = (int) Math.ceil((double) totalCount / perPageNum);

	    model.addAttribute("totalPageCount", totalPageCount);
	    model.addAttribute("currentPage", page); // 현재 페이지 정보도 전달
	    model.addAttribute("name", name);
	    model.addAttribute("userNumber", userNumber);

	    return "user/myPage/myPageLoanList";
	}
    
    
    
	
	// 내 도서 예약관리
	@GetMapping("/myPageReservationList.do")
	public String myPageReservationList() {
		return "user/myPage/myPageReservationList";
	}
	
	
	
	

}
