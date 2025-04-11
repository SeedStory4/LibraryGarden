package libraryGarden.admin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	    model.addAttribute("userList", userList);
	    model.addAttribute("totalCount", userList.size()); // 총 개수 전달
	    return "admin/user/userList"; // JSP 경로
	}


    @GetMapping("/userDetail.do")
    public String userDetail() {
        return "admin/user/userDetail";
    }

    @GetMapping("/userModify.do")
    public String userModify() {
        return "admin/user/userModify";
    }
}
