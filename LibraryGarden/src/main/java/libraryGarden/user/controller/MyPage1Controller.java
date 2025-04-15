package libraryGarden.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String myPageReservationList(
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
	    Map<String, Object> reservationList = myPage1Service.getUserReservationInfo(userNumber, page, perPageNum);
	    model.addAttribute("reservationList", reservationList);

	    int totalCount = (int) reservationList.get("totalCount");
	    int totalPageCount = (int) Math.ceil((double) totalCount / perPageNum);

	    model.addAttribute("totalPageCount", totalPageCount);
	    model.addAttribute("currentPage", page); // 현재 페이지 정보도 전달
	    model.addAttribute("name", name);
	    model.addAttribute("userNumber", userNumber);

	    return "user/myPage/myPageReservationList";
	}
	
    // 연장 요청 처리 (대출 연장)
    @ResponseBody
    @PostMapping("/extendLoan.do")
    public Map<String, Object> extendLoan(
            @RequestParam("lidx") int lidx,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        UserVo loginUser = (UserVo) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("success", false);
            result.put("message", "로그인 후 이용해주세요.");
            return result;
        }
        String userNumber = loginUser.getUserNumber();
        try {
            boolean success = myPage1Service.extendLoan(lidx, userNumber);
            if (success) {
                result.put("success", true);
                result.put("message", "연장이 완료되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "연장이 불가능합니다. 대출현황 또는 예약현황을 확인해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "연장 처리 중 오류가 발생했습니다.");
        }
        return result;
    }
}
	
	
	

