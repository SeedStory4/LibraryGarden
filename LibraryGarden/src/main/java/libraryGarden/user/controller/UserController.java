package libraryGarden.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.domain.UserVo;
import libraryGarden.user.service.UserService;

@Controller
@RequestMapping("/user/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource(name = "userService")
	private UserService userService;
	
	
	
//	@Resource(name = "userService")
//	private UserVo userVo;
	
	
	
//	@GetMapping("/userPrivacyPolicy.do")
//	public String userPrivacyPolicy() {
//		logger.info("userPrivacyPolicy enter");
//		return "user/user/userPrivacyPolicy";
//	}
//	
	@GetMapping("/userJoinForm.do")

	public String userJoinForm() {

	logger.info("약관 동의 완료 → 회원가입 입력 페이지로 이동");

	return "user/user/userJoin"; // 실제 회원가입 입력 form

	}


	// 회원가입페이지로 이동전 반드시 약관동의로 가야하기 때문에 경로 변경

	@GetMapping("/userJoin.do")

	public String userJoin() {

	return "user/user/userPrivacyPolicy";

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
            rttr.addFlashAttribute("joinSuccessMessage", "회원가입이 완료되었습니다.");
            return "redirect:/user/user/userLogin.do";
        } catch (Exception e) {
            logger.error("회원가입 중 오류 발생", e);
            rttr.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
            return "redirect:/user/user/userJoin.do";
        }
    }
    
    @PostMapping("/loginAction.do")
    public String login(UserVo userVo, HttpSession session, RedirectAttributes rttr) {
        UserVo loginUser = userService.login(userVo);

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            
            String role = loginUser.getRole();
            
            if("도서관장".equals(role) || "사서".equals(role)) {
            	return "redirect:/admin/main.do";
            }
            else {
            	return "redirect:/user/main.do"; // 로그인 성공 시 메인 페이지로
           }
        } else {
        	rttr.addFlashAttribute("loginFailMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
        	return "redirect:/user/user/userLogin.do"; // 다시 로그인 페이지로
        }
    }
    
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 초기화
        return "redirect:/user/main.do"; // 로그아웃 후 메인 페이지로 이동
    }
    
    @ResponseBody
    @RequestMapping(value = "/checkId.do", method = RequestMethod.POST)
    public String checkId(@RequestParam("id") String id) {
        int count = userService.countUserById(id);
        return (count == 0) ? "OK" : "DUPLICATE";
    }
    
    
    @PostMapping("/findIdAction.do")
    public String findIdAction(
        @RequestParam("name") String name,
        @RequestParam("phone") String phone,
        Model model,
        RedirectAttributes rttr
    ) {
        String foundId = userService.findIdByNameAndPhone(name, phone);

        if (foundId != null) {
        	rttr.addFlashAttribute("foundId", foundId); // 결과 페이지에서 사용
            return "redirect:/user/user/userFoundId.do"; // 아이디 찾기 결과 페이지로 이동
        } else {
            rttr.addFlashAttribute("errorMessage", "일치하는 회원 정보가 없습니다.");
            return "redirect:/user/user/userSearchId.do";
        }
    }
    
    
    @PostMapping("/findPasswordAction.do")
    public String findPasswordAction(
        @RequestParam("id") String id,
        @RequestParam("phone") String phone,
        RedirectAttributes rttr
    ) {
        // 1. 이메일 조회
        String email = userService.findEmailByIdAndPhone(id, phone);

        if (email == null) {
            rttr.addFlashAttribute("errorMessage", "일치하는 회원 정보가 없습니다.");
            return "redirect:/user/user/userSearchPassword.do";
        }

        // 2. 임시 비밀번호 생성
        String tempPassword = userService.generateTempPassword();

        // 3. DB에 암호화된 임시 비밀번호 저장
        userService.updatePasswordByPhone(id, phone, tempPassword); // ← phone + id 기준

        // 4. 이메일로 전송
        userService.sendTempPassword(email, tempPassword);

        // 5. 결과 페이지로 이동
        return "redirect:/user/user/userFoundPassword.do";
    }


  


    
    

}
