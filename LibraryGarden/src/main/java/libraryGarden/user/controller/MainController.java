package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class MainController {
	// 도서 예약
		@GetMapping("/main.do")
		public String main() {
			return "user/main";
		}
		
		@GetMapping("/userHeader.do")
	    public String userHeader() {
	        return "user/userHeader";		  
	    }

}
