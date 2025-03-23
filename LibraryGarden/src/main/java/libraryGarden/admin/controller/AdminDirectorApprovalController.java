package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.admin.service.AdminDirectorApprovalService;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;

@Controller
@RequestMapping("/admin/directorApproval")
public class AdminDirectorApprovalController {
	
private static final Logger logger = LoggerFactory.getLogger(AdminDirectorApprovalController.class);
	
	@Autowired(required=false)
	private AdminDirectorApprovalService directorApprovalService;
	
	@Autowired(required=false)
	private PageMaker pm;
	
	@RequestMapping(value="/directorApprovalList.do")
	public String directorApprovalList(
			SearchCriteria scri,
			ApprovalDto ad,
			Model model) {
		 
		 logger.debug("📝 directorApprovalList 들어옴");
		
		 pm.setScri(scri);
		 String filter = ad.getStatus();
		 
		 int cnt = directorApprovalService.directorApprovalTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 ArrayList<ApprovalDto> alist = directorApprovalService.directorApprovalSelectAll(scri, filter);
		 
		 model.addAttribute("alist", alist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		return "admin/directorApproval/directorApprovalList";
	}
	
    @GetMapping("/popDirectorApprovalRejectionWrite.do")
    public String popDirectorApprovalRejectionWrite() {
        return "admin/directorApproval/popDirectorApprovalRejectionWrite";
    }
}
