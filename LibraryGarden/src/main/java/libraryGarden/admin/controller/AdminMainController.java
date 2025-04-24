package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.admin.service.AdminApprovalService;
import libraryGarden.admin.service.AdminBookRequestService;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
	
		@Autowired
		private AdminBookRequestService adminBookRequestService;
	
		@Autowired
		private AdminApprovalService adminApprovalService;
	
	 	@GetMapping("/main.do")
	    public String main(Model model) {
	        
	 		SearchCriteria scri = new SearchCriteria(); // 기본 생성자면 null 값 처리됨
	 	    scri.setPage(1);
	 	    scri.setPerPageNum(4);

	 	    String filter = ""; // 전체 목록
	 	    ArrayList<RequestDto> requestList = adminBookRequestService.getBookRequestSelectAll(scri, filter);
	 	    ArrayList<ApprovalDto> approvalList = adminApprovalService.approvalSelectAll(scri, filter);
	 	    

	 	    model.addAttribute("requestList", requestList);
	 	    model.addAttribute("approvalList", approvalList);
	 		
	 		
	 		return "admin/main";
	    }

	   

}
