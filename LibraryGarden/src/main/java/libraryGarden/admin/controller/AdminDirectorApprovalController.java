package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.admin.service.AdminDirectorApprovalService;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.BookVo;
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

		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 결재 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = ad.getStatus();

		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = directorApprovalService.directorApprovalTotalCount(scri, filter);
		 pm.setTotalCount(cnt);

		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<ApprovalDto> alist = directorApprovalService.directorApprovalSelectAll(scri, filter);

		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("alist", alist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		return "admin/directorApproval/directorApprovalList";
	}
	
	@RequestMapping(value="/{aidx}/directorApprovalDetail.do")
	public String directorApprovalDetail(
			@PathVariable("aidx") int aidx,
			Model model) {
		 
		logger.debug("📝 directorApprovalDetail 들어옴");

		// 도서 정보 DB에서 가져오기
		BookVo bv = directorApprovalService.directorApprovalSelectOne(aidx);
		
		model.addAttribute("bv", bv);
		model.addAttribute("aidx", aidx);
		
		return "admin/directorApproval/directorApprovalDetail";
	}
	
	@PostMapping(value="/{aidx}/directorApprovalDeleteAction.do")
	public String boardDeleteAction(
			@PathVariable("aidx") int aidx,
			RedirectAttributes rttr) {
		
		logger.info("directorApprovalDeleteAction 들어옴");		
		
		// 해당 결재 게시글의 delyn 값 Y로 변경하기
		int value = directorApprovalService.directorApprovalDelete(aidx);

		// 삭제 후 이동할 url 및 메세지 설정 
		String path = "redirect:/admin/directorApproval/directorApprovalList.do";
		rttr.addFlashAttribute("msg", "삭제되었습니다.");
		
		// 삭제 실패시 이동할 url 및 메세지 설정
		if(value == 0) {
			path = "redirect:/admin/directorApprival/" + aidx + "/directorApprovalDetail.do";
			rttr.addFlashAttribute("msg", "삭제가 실패했습니다.");
		}
		
		return path;
	}

    @GetMapping("/popDirectorApprovalRejectionWrite.do")
    public String popDirectorApprovalRejectionWrite() {
        return "admin/directorApproval/popDirectorApprovalRejectionWrite";
    }
    
}
