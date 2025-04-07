package libraryGarden.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.domain.UserVo;
import libraryGarden.user.service.UserService;

@Controller
@RequestMapping("/user/myPage")
public class MyPage4Controller {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	// 내 도서 대출이력관리
		@GetMapping("/myPageModify.do")
		public String myPageModify(HttpSession session, Model model) {
			
			UserVo loginUser = (UserVo) session.getAttribute("loginUser");
			if (loginUser == null) {
				return "redirect:/user/user/userLogin.do"; // 로그인 안 되어 있으면 로그인 페이지로
		    }
			// DB에서 최신 정보 가져오기 (비밀번호 제외)
		    UserVo userInfo = userService.selectUserById(loginUser.getId());
		    model.addAttribute("user", userInfo);
				return "user/myPage/myPageModify";
		}
		
	  @PostMapping("/userUpdate.do")
	    public String updateUser(
	        @RequestParam("NowPassword") String nowPassword,
	        @RequestParam(value = "password", required = false) String password,
	        @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
	        @RequestParam("phone") String phone,
	        @RequestParam("email") String email,
	        @RequestParam("address") String address,
	        HttpSession session,
	        RedirectAttributes redirectAttrs
	    ) {
	        UserVo loginUser = (UserVo) session.getAttribute("loginUser");

	        // 현재 비밀번호 확인
	        if (!passwordEncoder.matches(nowPassword, loginUser.getPassword())) {
	            redirectAttrs.addFlashAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
	            return "redirect:/user/myPage/myPageModify.do";
	        }

	        // 비밀번호가 입력된 경우 → 유효성 검사 + 업데이트
	        String newPassword = loginUser.getPassword(); // 기본값: 기존 비밀번호

	        if (password != null && !password.isEmpty()) {
	            if (!password.equals(passwordConfirm)) {
	                redirectAttrs.addFlashAttribute("error", "새 비밀번호가 일치하지 않습니다.");
	                return "redirect:/user/myPage/myPageModify.do";
	            }
	            newPassword = passwordEncoder.encode(password);
	        }

	        // 업데이트할 정보 구성
	        UserVo updateUser = new UserVo();
	        updateUser.setId(loginUser.getId());
	        updateUser.setPassword(newPassword);
	        updateUser.setPhone(phone);
	        updateUser.setEmail(email);
	        updateUser.setAddress(address);

	        userService.updateUser(updateUser);

	        // 세션 정보도 최신화
	        loginUser.setPhone(phone);
	        loginUser.setEmail(email);
	        loginUser.setAddress(address);
	        loginUser.setPassword(newPassword); // 비번도 갱신 (보안 강화)

	        redirectAttrs.addFlashAttribute("msg", "회원정보가 성공적으로 수정되었습니다.");
	        return "redirect:/user/user/userLogin.do";
	    }

}
