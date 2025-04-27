package libraryGarden.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.admin.service.AdminBookRequestService;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.RequestVo;
import libraryGarden.domain.SearchCriteria;

@Controller
@RequestMapping("/admin/bookRequest")
public class AdminBookRequestController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminBookRequestController.class);

	// AdminBookRequestService 주입
	@Autowired(required=false)
	private AdminBookRequestService bookRequestService;
	
	// PageMaker 주입 (페이징 기능)
	@Autowired(required=false)
	private PageMaker pm;
	

	/**
	 * [설명] 관리자의 희망 도서 관리 페이지
	 * 
	 * [주요기능]
	 * - 관리자 희망 도서 목록 페이지 이동(이하 희망 도서 목록 페이지 이동)
	 * - 관리자 희망 도서 상세 페이지 이동(이하 희망 도서 상세 페이지 이동)
	 * - 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	 *  
	 * @author SiYeon
	 * @write 2024.03.23
	 */
	
	// 희망 도서 목록 페이지 이동
	@GetMapping("/bookRequestList.do")
	public String bookRequestList( SearchCriteria scri, RequestVo rd, Model model) {
		 logger.debug("AdminBookRequestService bookRequestList 들어옴");
		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);
		 
		 // 현재 탭의 신청 상태 저장
		 String filter = rd.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 
		 int cnt = bookRequestService.getBookRequestTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		 
		 // 목록에서 보여줄 데이터 
		 ArrayList<RequestDto> rlist = bookRequestService.getBookRequestSelectAll(scri, filter);
		 
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
		
		Map<String, Object> rq = bookRequestService.getBookRequestSelectone(rqidx);
		
		model.addAttribute("rq", rq);
		
		return "admin/bookRequest/bookRequestDetail";
	}

	// 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	@PostMapping("/bookRequestModify.do")
	public String bookRequestModify(@RequestParam("status") String status, @RequestParam("rqidx") int rqidx,
		    @RequestParam(value = "rejectionReason", required = false) String rejectionReason,Model model) {
		 logger.debug("AdminBookRequestService bookRequestModify 들어옴");
		
		int value = bookRequestService.modifyBookRequest(rqidx, status, rejectionReason);
		
		if(value == 0) {
			model.addAttribute("msg", "등록을 실패했습니다. 잠시 후 다시 시도해주세요.");
			return "redirect:/admin/bookRequest/"+rqidx+"/bookRequestDetail.do";

		}
		model.addAttribute("msg", "등록 성공했습니다");
		return "redirect:/admin/bookRequest/"+rqidx+"/bookRequestDetail.do";
	}
	
	
	/**
	 * [주요기능]
	 * - 희망 도서 목록 페이지 이동(팝업)
	 *  
	 * @author JiHye
	 * @write 2024.03.23
	 */
	
	// 희망 도서 목록 페이지 이동(팝업)
	@ResponseBody
	@RequestMapping(value="/bookRequestList.do", method = RequestMethod.POST)
	public HashMap<String, Object> bookRequestList(
			@RequestParam(value = "searchType", defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page
		 ) {
					
		 logger.debug("bookRequestList 들어옴");
		 
		 // 사용자가 입력한 검색조건과 검색어 저장
		 SearchCriteria scri = new SearchCriteria();
		 scri.setSearchType(searchType);
		 scri.setKeyword(keyword);
		 scri.setPage(page);
		 pm.setScri(scri);
		 
		 // 희망도서 목록 중 "신청대기" 상태의 데이터만 보여주기 위해서 filter 설정
		 String filter = "신청대기";
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = bookRequestService.bookRequestTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<RequestDto> alist = bookRequestService.bookRequestSelectAll(scri, filter);
		 
		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 HashMap<String, Object> hm = new HashMap<String, Object>();
		 hm.put("alist", alist);
		 hm.put("pm", pm);
		 
		 return hm;
		
	}
   
}