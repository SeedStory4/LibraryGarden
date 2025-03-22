package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/myPage")
public class MyPage4Controller {
	
	
	// 내 도서 대출이력관리
		@GetMapping("/myPageModify.do")
		public String myPageModify() {
			return "user/myPage/myPageModify";
		}

}
