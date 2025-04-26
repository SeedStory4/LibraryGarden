package libraryGarden.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.user.service.BookRequestService;
import libraryGarden.user.service.MyPage1Service;

/**
 * [설명] 사용자의 개인 페이지 - 희망 도서 신청한 목혹 (신청을 해서 목록으로 들어옴)
 * 
 * [주요기능]
 * - 도서신청관리(희망 도서 신청한 목록) 페이지 이동
 * - 신청한 희망 도서 삭제
 * 
 * @author SiYeon
 */
@Controller
@RequestMapping("/user/myPage")
public class MyPage2Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(MyPage2Controller.class);
	
	// MyPage1Service 주입
    @Autowired
    private MyPage1Service myPage1Service;
    
	// BookRequestService 주입
	@Autowired(required=false)
	BookRequestService bookRequestService;
	
	
	// 도서신청관리(희망 도서 신청한 목록) 페이지 이동
	@GetMapping("/myPageRequestList.do")
	public String myPageRequestList(@RequestParam(value = "page", required = false, defaultValue = "1") int  page, HttpSession session, Model model) throws Exception {
		logger.debug("MyPage2Controller myPageRequestList 들어옴");
		
		// 로그인 확인
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/user/user/userLogin.do"; // 로그인 안 되어 있으면 로그인 페이지로
	    }
		
		// 로그인 한 유저 대출상태 확인
		String loanStatus = myPage1Service.getUserLoanStatus(loginUser.getUserNumber()); 
	    
		// 유저의 희망도서 신청 리스트 출력
		List<Map<String, Object>> requestList = bookRequestService.getUserRequestInfo(loginUser.getUidx());
	    
		// 페이징
		int cnt = bookRequestService.getUserRequestInfoTotalCount(loginUser.getUidx()); // 전체 수
	    PageMaker pm = new PageMaker();
	    SearchCriteria scri = new SearchCriteria();
	    scri.setPage(page);
	    pm.setScri(scri);
	    pm.setTotalCount(cnt);
	    
	    model.addAttribute("uv", loginUser);// 유저 정보
	    model.addAttribute("loanStatus", loanStatus); // 대출가능 정보
	    model.addAttribute("requestList", requestList); // 희망도서 신청 정보
	    model.addAttribute("pm", pm); // 페이징
	    
		return "user/myPage/myPageRequestList";
	}

	// 신청한 희망 도서 삭제
	@PostMapping("/deleteRequest.do")
	public String deleteRequest(@RequestParam(value = "page", required = false, defaultValue = "1") int  page, @RequestParam("bidx") int bidx, @RequestParam("rqidx") int rqidx, HttpSession session, Model model) throws Exception {
		logger.debug("MyPage2Controller deleteRequest 들어옴");
		
		// 로그인 정보 세션값에서 가지고 오기
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		
		// 삭제하고 값 가지고 오기
		int value = bookRequestService.deleteRequest(loginUser.getUidx(),bidx,rqidx);
		System.out.println(value);
		
		if(value == 0) {
			model.addAttribute("msg", "희망도서 삭제를 실패했습니다. 잠시 후 다시 시도해주세요.");
			return "redirect:/user/myPage/myPageRequestList.do?page="+page;
		}
		return "redirect:/user/myPage/myPageRequestList.do?page="+page;
	}
}
