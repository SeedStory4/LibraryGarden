package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
	
	 	@GetMapping("/main.do")
	    public String main() {
	        return "admin/main";
	    }

	    @GetMapping("/cmm/footer.do")
	    public String footer() {
	        return "cmm/footer";
	    }
	    @GetMapping("/adminHeader.do")
	    public String adminHeader() {
	        return "admin/adminHeader";
	    }

}
