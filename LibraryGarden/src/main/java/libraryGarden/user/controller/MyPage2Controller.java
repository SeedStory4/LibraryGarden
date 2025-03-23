package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * [설명] 사용자의 개인 페이지 - 희망 도서 신청한 목혹 (신청을 해서 목록으로 들어옴)
 * 
 * [주요기능]
 * - 도서신청관리(희망 도서 신청한 목록) 페이지 이동
 *  
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/user/myPage")
public class MyPage2Controller {
	
	
	// 도서신청관리(희망 도서 신청한 목록) 페이지 이동
	@GetMapping("/myPageRequestList.do")
	public String myPageRequestList() {
		return "user/myPage/myPageRequestList";
	}

}
