package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	 @GetMapping("/user/main.do")
	    public String main() {
	        return "user/main";
	    }

	    @GetMapping("/user/userHeader.do")
	    public String userHeader() {
	        return "user/userHeader";
	    }
	    
	    @GetMapping("/cmm/footer.do")
	    public String footer() {
	        return "cmm/footer";
	    }
	    @GetMapping("/admin/adminHeader.do")
	    public String adminHeader() {
	        return "admin/adminHeader";
	    }

}
