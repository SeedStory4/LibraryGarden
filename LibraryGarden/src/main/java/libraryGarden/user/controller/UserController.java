package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/user")
public class UserController {
	
	@GetMapping("/userPrivacyPolicy.do")
	public String userPrivacyPolicy() {
		return "user/user/userPrivacyPolicy";
	}
	
	@GetMapping("/userJoin.do")
    public String userJoin() {
        return "user/user/userJoin";
    }

    @GetMapping("/userLogin.do")
    public String userLogin() {
        return "user/user/userLogin";
    }

    @GetMapping("/userSearchId.do")
    public String userSearchId() {
        return "user/user/userSearchId";
    }

    @GetMapping("/userFoundId.do")
    public String userFoundId() {
        return "user/user/userFoundId";
    }

    @GetMapping("/userSearchPassword.do")
    public String userSearchPassword() {
        return "user/user/userSearchPassword";
    }

    @GetMapping("/userFoundPassword.do")
    public String userFoundPassword() {
        return "user/user/userFoundPassword";
    }

}
