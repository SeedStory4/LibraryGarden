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
		    RedirectAttributes rttr
		) {
		    UserVo loginUser = (UserVo) session.getAttribute("loginUser");

		    // 현재 비밀번호 확인
		    if (!passwordEncoder.matches(nowPassword, loginUser.getPassword())) {
		        rttr.addFlashAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
		        return "redirect:/user/myPage/myPageModify.do";
		    }

		    // 기본값: 기존 비밀번호
		    String newPassword = loginUser.getPassword();		

		    // 새 비밀번호 입력된 경우에만 변경
		    if (password != null && !password.trim().isEmpty()) {
		        if (!password.equals(passwordConfirm)) {
		            rttr.addFlashAttribute("error", "새 비밀번호가 일치하지 않습니다.");
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
		    loginUser.setPassword(newPassword);
		    loginUser.setPhone(phone);
		    loginUser.setEmail(email);
		    loginUser.setAddress(address);
		    
		    

		    if (password != null && !password.trim().isEmpty()) {
		        // 비밀번호 변경 처리...
		        rttr.addFlashAttribute("logoutMsg", "비밀번호가 변경되었습니다. 변경된 비밀번호로 다시 로그인해주세요.");
		    } else {
		        rttr.addFlashAttribute("logoutMsg", "회원정보가 성공적으로 수정되었습니다.");
		    }
		    // 로그아웃: 세션 초기화
		    session.invalidate();

		    return "redirect:/user/user/userLogin.do";
		}

}
