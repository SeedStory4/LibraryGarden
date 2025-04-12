package libraryGarden.admin.controller;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import libraryGarden.admin.service.AdminBookRequest2Service;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.RequestVo;
import libraryGarden.domain.SearchCriteria;

/**
 * [설명] 관리자의 희망 도서 관리 페이지
 * 
 * [주요기능]
 * - 관리자 희망 도서 목록 페이지 이동(이하 희망 도서 목록 페이지 이동)
 * 
 * - 관리자 희망 도서 상세 페이지 이동(이하 희망 도서 상세 페이지 이동)
 * 
 * 
 * 
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/admin/bookRequest")
public class AdminBookRequest2Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminBookRequest2Controller.class);

	// AdminBookRequest2Service 주입
	@Autowired(required=false)
	private AdminBookRequest2Service bookRequestService2;
	
	// PageMaker 주입 (페이징 기능)
	@Autowired(required=false)
	private PageMaker pm;
	
	// 희망 도서 목록 페이지 이동
	@GetMapping("/bookRequestList.do")
	public String bookRequestList( SearchCriteria scri, RequestVo rd, Model model) {
		 logger.debug("AdminBookRequest2Service bookRequestList 들어옴");
		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);
		 
		 // 현재 탭의 신청 상태 저장
		 String filter = rd.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 
		 int cnt = bookRequestService2.getBookRequestTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		 
		 // 목록에서 보여줄 데이터 
		 ArrayList<RequestDto> rlist = bookRequestService2.getBookRequestSelectAll(scri, filter);
		 
		 // "\" 등 검색시 오류 발생하지 않도록 검색어 encoding
		 UrlEncoder encoder = new UrlEncoder();		 
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("rlist", rlist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		 
		return "admin/bookRequest/bookRequestList";
	}
	
	// 희망 도서 상세 페이지 이동
	@GetMapping("/{rqidx}/bookRequestDetail.do")
	public String bookRequestDetail(@PathVariable("rqidx") int rqidx, Model model) {
		
		Map<String, Object> rq = bookRequestService2.getBookRequestSelectone(rqidx);
		
		model.addAttribute("rq", rq);
		
		return "admin/bookRequest/bookRequestDetail";
	}

	// 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	@PostMapping("/bookRequestModify.do")
	public String bookRequestModify(@RequestParam("status") String status, @RequestParam("rqidx") int rqidx,
		    @RequestParam(value = "rejectionReason", required = false) String rejectionReason,Model model) {
		System.out.println("AdminBookRequest2Service bookRequestModify 들어옴");
		
		int value = bookRequestService2.modifyBookRequest(rqidx, status, rejectionReason);
		System.out.println("AdminBookRequest2Service bookRequestModify value"+value);
		
		if(value == 0) {
			model.addAttribute("msg", "등록을 실패했습니다. 잠시 후 다시 시도해주세요.");
			return "redirect:/admin/bookRequest/"+rqidx+"/bookRequestDetail.do";

		}
		model.addAttribute("msg", "등록 성공했습니다");
		return "redirect:/admin/bookRequest/"+rqidx+"/bookRequestDetail.do";
	}


   
}