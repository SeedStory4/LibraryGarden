package libraryGarden.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.admin.service.AdminApproval2Service;
import libraryGarden.admin.service.AdminBookService;
import libraryGarden.admin.service.AdminLibraryBooksService;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.controller.Book1Controller;
import libraryGarden.user.service.LibraryBooksService;

/**
 * [설명] 관리자의 도서 관리 페이지
 * 
 * [주요기능] 
 * - 관리자 도서관 도서 조회 목록 페이지 
 * 	- 도서 조회 목록 출력 기능 
 * 	- 도서 조회 검색 기능 
 * 	- 도서 조회 페이지 기능
 * 
 * - 관리자 도서관 도서 삭제 기능 
 * 
 * - 관리자 도서관 도서 상세 페이지 이동
 * 
 * - 관리자 도서관 도서 수정 페이지 이동
 * 
 * - 관리자 도서관 도서 등록 페이지 이동
 * 
 * - 관리자 도서 선택 팝업 페이지 이동
 * 
 * 
 * @author SiYeon
 * 
 */
@Controller
@RequestMapping("/admin/book")
public class AdminBookController {

	private static final Logger logger = LoggerFactory.getLogger(AdminBookController.class);

	// AdminApproval2Service 주입
	@Autowired(required = false)
	private AdminApproval2Service adminApprovalService;
	
	// AdminLibraryBooksService 주입
	@Autowired(required = false)
	private AdminLibraryBooksService adminLibraryBooksService;
	
	// PageMaker 주입 (페이징 기능)
	@Autowired(required = false)
	private PageMaker pm;

	// 도서 조회 목록 페이지 이동
	@GetMapping("/bookList.do")
	public String bookList(SearchCriteria scri, Model model) {
		/*
		 * 검색 기능 - 사용자가 입력한 검색조건과 검색어 저장 
		 * [input] 검색조건 searchType / 검색어 keyword 외 페이지기능 (scri)
		 * 
		 */
		pm.setScri(scri);

		/*
		 * 페이징 기능 - 책 리스트 전체 갯수 - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기 
		 * [input] 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri)
		 * [output] 조건에 따른 잭 전체 개수 cnt
		 */
		int cnt = adminLibraryBooksService.getBookTotalCount(scri);

		/*
		 * 페이지 기능 
		 * [input] 조건에 따른 책 전체 개수 cnt
		 */
		pm.setTotalCount(cnt);

		/*
		 * 책 목록 조회 
		 * [input] 검색조건 외 페이지 기능 (scri) 
		 * [output] 책 목록(alist)
		 */
		ArrayList<LibraryBookDto> lblist = adminLibraryBooksService.getBookSelectAll(scri);

		/*
		 * Model를 통해 jsp로 이동 
		 * - lblist : 책 목록 리스트 
		 * - pm : 페이징 기능
		 */
		model.addAttribute("lblist", lblist);
		model.addAttribute("pm", pm);
		return "admin/book/bookList";
	}

	// 도서 상세 페이지 이동
	@GetMapping("/{lbidx}/bookDetail.do")
	public String bookDetail(@PathVariable("lbidx") int lbidx, Model model) {
		logger.debug("bookDetail 들어옴");

		/*
		 * 도서관 책 상세 조회 
		 * [input] 도서관 책 인덱스(lbidx) 
		 * [output] 책 상세(lbd)
		 */
		LibraryBookDto lbd = adminLibraryBooksService.getBookSelectOne(lbidx);
		
		/*
		 * Model를 통해 jsp로 이동 
		 * - lbd : 책 상세 내용
		 */
		model.addAttribute("lbd", lbd);
		return "admin/book/bookDetail";
	}

	// 관리자 도서관 도서 삭제 기능
	@GetMapping("/{lbidx}/bookDelete.do")
	public String bookDelete(@PathVariable("lbidx") int lbidx, RedirectAttributes rttr) {
		logger.debug("bookDelete 들어옴");
		
		/*
		 * 도서관 책 삭제
		 * [input] 도서관 책 인덱스(lbidx) 
		 */
		int value = adminLibraryBooksService.getBookDeleteOne(lbidx);
		if (value==0) {
			rttr.addFlashAttribute("msg", "삭제하지 못했습니다.");
			return "redirect:/admin/book/"+lbidx+"/bookDetail.do"; //삭제하지 못했을 때 삭제하려고 했던 페이지로 이동 
		}	
		return "redirect:/admin/book/bookList.do"; // 삭제 후 리스트로 이동

	}

	// 관리자 도서관 도서 수정 페이지 이동
	@GetMapping("/{lbidx}/bookModify.do")
	public String bookModify(@PathVariable("lbidx") int lbidx,Model model) {
		logger.debug("bookModify 들어옴");
		
		/*
		 * 도서관 책 상세 조회 
		 * [input] 도서관 책 인덱스(lbidx) 
		 * [output] 책 상세(lbd)
		 */
		LibraryBookDto lbd = adminLibraryBooksService.getBookSelectOne(lbidx);
		
		/*
		 * Model를 통해 jsp로 이동 
		 * - lbd : 책 상세 내용
		 */
		model.addAttribute("lbd", lbd);
		return "admin/book/bookModify";

	}
	
//	// 도서 수정 페이지 이동
//	@GetMapping("/bookModify.do")
//	public String bookModify() {
//		return "admin/book/bookModify";
//	}

	// 도서 등록 페이지 이동
	@GetMapping("/bookWrite.do")
	public String bookWrite() {
		return "admin/book/bookWrite";
	}

	// 도서 선택 팝업 페이지 이동
	@GetMapping("/popBookSelect.do")
	public String popBookSelect() {
		return "admin/book/popBookSelect";
	}
	
	// 도서 선택 팝업 페이지 이동 ajax
	@PostMapping("/bookSelectList.do")
	@ResponseBody
	public HashMap<String, Object> bookSelectList(
			@RequestParam(value = "searchType", defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "selectedAidx", defaultValue = "1") int selectedAidx
	) {
		
		logger.info("bookList 들어옴");
		
		 // 사용자가 입력한 검색조건과 검색어 저장
		 SearchCriteria scri = new SearchCriteria();
		 scri.setSearchType(searchType);
		 scri.setKeyword(keyword);
		 scri.setPage(page);
		 pm.setScri(scri);
		 
		 // 결재도서 중 "승인" 상태의 데이터만 보여주기 위해서 filter 설정
		 String filter = "승인";
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = adminApprovalService.getBookApprovalTotalCount(scri, filter, selectedAidx);
		 pm.setTotalCount(cnt);
		 
		 
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 List<Map<String, Object>> blist = adminApprovalService.getBookApprovalSelectAll(scri, filter, selectedAidx);
		 
		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 HashMap<String, Object> hm = new HashMap<String, Object>();
		 hm.put("blist", blist);
		 hm.put("pm", pm);
		 
		 return hm;
	}
	
	// 도서 선택 팝업에서 선택한 책 정보 가지고오기 ajax
	@PostMapping("/bookSelectOne")
	@ResponseBody
	public BookVo bookSelectOne(@RequestParam(value = "aidx", defaultValue = "1") int aidx) {
		
		logger.info("bookSelectOne 들어옴");
		
		 // 동록에서 보여줄 데이터 DB에서 가져오기
		 BookVo bv = adminApprovalService.getBookApprovalSelectOne(aidx);
		 
		 return bv;
	}
}
