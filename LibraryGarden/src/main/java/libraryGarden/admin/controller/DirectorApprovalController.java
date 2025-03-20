package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/directorApproval")
public class DirectorApprovalController {
	
    @GetMapping("/popDirectorApprovalRejectionWrite.do")
    public String popDirectorApprovalRejectionWrite() {
        return "admin/directorApproval/popDirectorApprovalRejectionWrite";
    }
}
