package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController2 {
	
    @GetMapping("/user/main.do")
    public String main() {
        return "user/main";
    }

    @GetMapping("/user/userHeader.do")
    public String userHeader() {
        return "user/userHeader";
    }
}
