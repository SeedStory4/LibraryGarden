package libraryGarden.user.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String myPageLoanList(HttpSession session, Model model) throws Exception {
        // 세션에서 loginUser 객체 가져오기
        UserVo loginUser = (UserVo) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/user/userLogin.do"; // 로그인 안 된 경우 로그인 페이지로 리디렉션
        }

        // loginUser 객체에서 필요한 정보 추출
        String userNumber = loginUser.getUserNumber(); // userNumber 가져오기
        String name = loginUser.getName(); // name 가져오기

        // 대출 가능 여부 조회
        String loanStatus = myPage1Service.getUserLoanStatus(userNumber);
        model.addAttribute("loanStatus", loanStatus);
        
        // 대출 목록 조회 (페이징 처리)
        Map<String, Object> loanList = myPage1Service.getUserLoanInfo(userNumber, 1, 12);
        model.addAttribute("loanList", loanList);
        System.out.println("Loan List나와요? " + loanList);

        // 사용자 이름 및 번호
        model.addAttribute("name", name);
        model.addAttribute("userNumber", userNumber);

        return "user/myPage/myPageLoanList"; // JSP 페이지로 데이터 전달
    }
    
    
    
	
	// 내 도서 예약관리
	@GetMapping("/myPageReservationList.do")
	public String myPageReservationList() {
		return "user/myPage/myPageReservationList";
	}
	
	
	
	

}
