package libraryGarden.admin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.user.controller.UserController;
import libraryGarden.user.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource(name = "userService")
	private UserService userService;

	@GetMapping("/userList.do")
	public String userList(Model model, SearchCriteria cri) {
	    List<UserVo> userList = userService.searchUsersByCriteria(cri);
	    int totalCount = userService.countUsers(cri);
	    
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setScri(cri);
	    pageMaker.setTotalCount(totalCount);
	    
	    model.addAttribute("userList", userList);
	    model.addAttribute("totalCount", totalCount); // 총 개수 전달
	    model.addAttribute("cri", cri);
	    model.addAttribute("pageMaker", pageMaker);
	    return "admin/user/userList"; // JSP 경로
	}


	@GetMapping("/userDetail.do")
	public String userDetail(@RequestParam("id") String id, Model model) {
	    UserVo user = userService.selectUserById(id);  // 이미 구현됨
	    model.addAttribute("user", user);
	    return "admin/user/userDetail";  // JSP 경로
	}


    @GetMapping("/userModify.do")
    public String userModify(@RequestParam("id") String id, Model model) {
    	UserVo user = userService.selectUserById(id);  // 이미 구현됨
	    model.addAttribute("user", user);
        return "admin/user/userModify";
    }
    
    @PostMapping("/userModifyAction.do")
    public String userModifyAction(UserVo user, RedirectAttributes rttr) {
        try {
            // 기존 유저 정보 조회
            UserVo original = userService.selectUserById(user.getId());

            // 비밀번호는 기존 값으로 유지
            user.setPassword(original.getPassword());

            // 업데이트 실행 (password 포함된 쿼리라면)
            userService.updateAdminUser(user);
            rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMessage", "수정 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/user/userDetail.do?id=" + user.getId();
    }


}
