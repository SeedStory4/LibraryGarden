package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @GetMapping("/userList.do")
    public String userList() {
        return "admin/user/userList";
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
