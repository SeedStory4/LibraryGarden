package libraryGarden.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		logger.info("userPrivacyPolicy enter");
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
            // 마지막 userNumber 가져오기
            String lastUserNumber = userService.getLastUserNumber();
            int nextNumber = (lastUserNumber == null) ? 1 : Integer.parseInt(lastUserNumber) + 1;
            String formattedNumber = String.format("%06d", nextNumber); // ex) 000001

            // userNumber 세팅
            user.setUserNumber(formattedNumber);

            userService.insertUser(user);
            logger.info("회원가입 성공 - ID: {}, userNumber: {}", user.getId(), formattedNumber);
            rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/user/user/userLogin.do";
        } catch (Exception e) {
            logger.error("회원가입 중 오류 발생", e);
            rttr.addFlashAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "redirect:/user/user/userJoin.do";
        }
    }
    
    @PostMapping("/loginAction.do")
    public String login(UserVo userVo, HttpSession session, Model model) {
        UserVo loginUser = userService.login(userVo);

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            return "redirect:/user/main.do"; // 로그인 성공 시 메인 페이지로
        } else {
            model.addAttribute("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/user/user/userLogin"; // 다시 로그인 페이지로
        }
    }
    
    

}
