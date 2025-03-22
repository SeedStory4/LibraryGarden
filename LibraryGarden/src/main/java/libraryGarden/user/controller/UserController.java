package libraryGarden.user.controller;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.domain.UserVo;
import libraryGarden.user.service.UserService;

@Controller
@RequestMapping("/user/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource(name = "userService")
	private UserService userService;
	
	
	
	
	@GetMapping("/userPrivacyPolicy.do")
	public String userPrivacyPolicy() {
		logger.info("userPrivacyPolicy enter - ID: {}");
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
    
    
    @PostMapping("/userJoinAction.do")
    public String userJoinAction(UserVo user, RedirectAttributes rttr) {
        try {
            userService.insertUser(user);
            logger.info("회원가입 성공 - ID: {}", user.getId());
            rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:user/user/userLogin.do"; // 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(" 회원가입 중 오류 발생", e);
            rttr.addFlashAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "redirect:user/user/userJoin.do"; // 회원가입 페이지로 되돌림
        }
    }
    
    

}
