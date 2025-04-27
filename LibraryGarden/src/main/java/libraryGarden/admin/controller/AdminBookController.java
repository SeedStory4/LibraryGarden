package libraryGarden.admin.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.admin.service.AdminApprovalService;
import libraryGarden.admin.service.AdminBookCUDService;
import libraryGarden.admin.service.AdminCategoryService;
import libraryGarden.admin.service.AdminLibraryBooksService;
import libraryGarden.cmm.util.AladdinOpenAPI;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.ApiBookPageDto;
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.LibraryBooksVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;


/**
 * [설명] 관리자 도서 조회 관련 controller
 * 
 * [주요기능] 
 *  - 도서 조회 목록 페이지 이동
 *  - 도서 상세 페이지 이동
 *  - 도서 상세에서 도서 삭제 기능
 *  - 도서 등록 페이지 이동 
 *  - 도서 등록 Action 기능
 *  - 도서 등록 시 선택 팝업 페이지 열기 ajax
 *  - 도서 등록 시 선택 팝업에서 선택한 책 정보 조회 ajax
 *  - 도서 등록/수정 시 카데고리 소분류 조회 ajax 
 *  - 도서 등록/수정 시 청구기호 일치여부 숫자 조회 ajax
 *  - 도서 등록에서 등록한 도서 리스트 조회 ajax
 *  - 도서 등록에서 등록한 도서 중에서 삭제 기능 ajax
 *  - 도서 수정 페이지 이동
 *  - 도서 수정 Action 기능
 * 
 * @author SiYeon
 */
@Controller
@RequestMapping("/admin/book")
public class AdminBookController {

	private static final Logger logger = LoggerFactory.getLogger(AdminBookController.class);

	// AdminApproval2Service 주입
	@Autowired(required = false)
	private AdminApprovalService adminApprovalService;
	
	// AdminLibraryBooksService 주입
	@Autowired(required = false)
	private AdminLibraryBooksService adminLibraryBooksService;
	
	// AdminCategoryService 주입
	@Autowired(required = false)
	private AdminCategoryService adminCategoryService;
	
	// AdminBookCUDService 주입
	@Autowired(required = false)
	private AdminBookCUDService adminBookCUDService;
	
	// PageMaker 주입 (페이징 기능)
	@Autowired(required = false)
	private PageMaker pm;
	
	@Autowired(required = false)
	AladdinOpenAPI aladdinOpenAPI;

	// 도서 조회 목록 페이지 이동
	@GetMapping("/bookList.do")
	public String moveBookList(SearchCriteria scri, Model model) {
 
		// 검색 기능 - 사용자가 입력한 검색조건과 검색어 저장 
		pm.setScri(scri);
 
		// 페이징 기능 - 책 리스트 전체 갯수 - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기 
		int cnt = adminLibraryBooksService.getBookTotalCount(scri);

		// 페이지 기능 
		pm.setTotalCount(cnt);

		// 책 목록 조회 
		ArrayList<LibraryBooksDto> lblist = adminLibraryBooksService.getBookSelectAll(scri);

		model.addAttribute("lblist", lblist);
		model.addAttribute("pm", pm);
		return "admin/book/bookList";
	}

	// 도서 상세 페이지 이동
	@GetMapping("/{lbidx}/bookDetail.do")
	public String moveBookDetail(@PathVariable("lbidx") int lbidx, Model model) {
		logger.debug("moveBookDetail 들어옴");

		// 도서관 책 상세 조회 
		LibraryBooksDto lbd = adminLibraryBooksService.getBookSelectOne(lbidx);
		
		model.addAttribute("lbd", lbd);
		return "admin/book/bookDetail";
	}
	
	// 도서 상세에서 도서 삭제 기능
	@GetMapping("/{lbidx}/bookDelete.do")
	public String deleteLibraryBooks(@PathVariable("lbidx") int lbidx, RedirectAttributes rttr) {
		logger.debug("deleteLibraryBooks 들어옴");
		
		// 도서관 책 삭제
		int value = adminBookCUDService.deleteLibraryBooksAndUpdateApproval(lbidx);
		if (value==0) {
			rttr.addFlashAttribute("msg", "삭제하지 못했습니다.");
			return "redirect:/admin/book/"+lbidx+"/bookDetail.do"; //삭제하지 못했을 때 삭제하려고 했던 페이지로 이동 
		}	
		return "redirect:/admin/book/bookList.do"; // 삭제 후 리스트로 이동

	}

	// 도서 등록 페이지 이동 
	@GetMapping("/bookWrite.do")
	public String getbookWrite(Model model) {
		
		/****도서 구분****/
		// 등록된 도서관 책 중 마지막 구분을 가지고 옮
		String lastCode = adminLibraryBooksService.getLibraryBookLastCode();
		if(lastCode == null  ) {
			lastCode = "ss000000";
		}
		// ss분리 
		String prefix = lastCode.replaceAll("[0-9]", "");
	    // 숫자 분리
	    String numberPart = lastCode.replaceAll("[^0-9]", ""); 
	    // 숫자 변환 및 +1
	    int number = Integer.parseInt(numberPart);
	    number++;
	    // 원래 자릿수에 맞춰 0 채우기
	    lastCode = prefix + String.format("%06d", number);
		
	    /**************/
	    
	    /****도서 분류(대분류)****/
	    // 도서 카테고리 대분류를 가지고 옴(level = 1)
	    List<LibraryBooksDto> parentList = adminCategoryService.getParentCategoryByLevel(); 
	    /**************/
	    
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("lastCode", lastCode);
		hm.put("parentList", parentList);

		model.addAttribute("hm", hm);
		
		return "admin/book/bookWrite";
	}

	// 도서 등록 Action 기능
	@PostMapping("/bookWriteAction.do")
	public String insertBookWriteAction(LibraryBooksVo lbv, Model model) {
		
		logger.debug("insertBookWriteAction 들어옴");
		
	    int result = adminBookCUDService.insertLibraryBooksAndUpdateApproval(lbv);

	    if (result == 2) {
	        return "redirect:/admin/book/bookWrite.do";
	    }
	    
	    model.addAttribute("msg", "도서 등록에 실패했습니다.");
	    return "redirect:/admin/book/bookWrite.do";
	}

	// 도서 등록 시 선택 팝업 페이지 열기 ajax
	@PostMapping("/bookSelectList.do")
	@ResponseBody
	public HashMap<String, Object> moveBookSelectList(
			@RequestParam(value = "searchType", defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "selectedAidx", defaultValue = "0") int selectedAidx) {
		
		logger.debug("moveBookSelectList 들어옴");
		
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
	
	// 도서 등록 시 선택 팝업에서 선택한 책 정보 조회 ajax
	@PostMapping("/bookSelectOne.do")
	@ResponseBody
	public HashMap<String, Object> getbookSelectOne(@RequestParam(value = "aidx", defaultValue = "1") int aidx) {

		logger.debug("bookSelectOne 들어옴");
		
		 // 동록에서 보여줄 데이터 DB에서 가져오기
		HashMap<String, Object> hm = adminApprovalService.getBookApprovalSelectOne(aidx);
		 
		return hm;
	}
	
	// 도서 등록/수정 시 카데고리 소분류 조회 ajax
	@PostMapping("/getChildrenCategory.do")
	@ResponseBody
	public List<LibraryBooksDto> getChildrenCategory(@RequestParam(value = "parentCode") int parentCode) {
		
		logger.debug("getChildrenCategory 들어옴");
		
	    // 도서 카테고리 소분류를 가지고 옴(level = 1)
		List<LibraryBooksDto> lbdList = adminCategoryService.getChildrenCategoryByparentCode(parentCode);
		 
		return lbdList;
	}
	
	// 도서 등록/수정 시 청구기호 일치여부 숫자 조회 ajax
	@PostMapping("/checkCallNumberDuplicate.do")
	@ResponseBody
	public int checkCallNumberDuplicate(@RequestParam(value = "callName") String callName,
		    @RequestParam(value = "lbidx", required = false) Integer lbidx,
		    @RequestParam(value = "aidx", required = false) Integer aidx,
		    @RequestParam(value = "bidx", required = false) Integer bidx) {
		
		logger.debug("checkCallNumberDuplicate 들어옴");

	    // lbidx, aidx, bidx 모두 있으면 => 수정시
	    if (lbidx != null && aidx != null && bidx != null) {
	    	LibraryBooksDto original = adminLibraryBooksService.getBookSelectOne(lbidx);

	        if (original == null) {
	            return -99;  // 오류 상황
	        }

	        boolean isSameBook = (original.getAidx() == aidx) && (original.getBidx() == bidx);
	        boolean isSameCallNumber = callName.equals(original.getCallName());
        
	        if (isSameBook && isSameCallNumber) {
	            return -1;  // 같은 책 + 같은 청구기호
	        } else {
	            int cnt = adminLibraryBooksService.getCheckCallNumberDuplicate(callName);
	            return cnt;  // 중복 개수 리턴
	        }
	    }else {
	    
		    // 청구기호 일치여부 숫자 => 등록시
			int cnt = adminLibraryBooksService.getCheckCallNumberDuplicate(callName); // 
			return cnt;
	    }
	}
	
	// 도서 등록에서 등록한 도서 리스트 조회 ajax
	@PostMapping("/bookWriteList.do")
	@ResponseBody
	public HashMap<String, Object> getBookWriteList(@RequestParam(value = "page", defaultValue = "1") int page) {
		
		logger.debug("bookWriteList 들어옴");
	    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    
		 // 페이징 저장
		 SearchCriteria scri = new SearchCriteria();
		 scri.setPage(page);
		 pm.setScri(scri);
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = adminLibraryBooksService.getBookWriteListCount(today);
		 pm.setTotalCount(cnt);
		 
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<LibraryBooksDto> bwlist = adminLibraryBooksService.getBookWriteListSelectAll(scri, today);
		 
		 HashMap<String, Object> bwhm = new HashMap<String, Object>();
		 bwhm.put("bwlist", bwlist);
		 bwhm.put("bwpm", pm);
		 
		 return bwhm;
	}

	// 도서 등록에서 등록한 도서 중에서 삭제 기능 ajax
	@PostMapping("/bookWriteDelete.do")
	@ResponseBody
	public String deleteLibraryBooksWrite(@RequestParam("lbidx") int lbidx) {
		logger.debug("deleteLibraryBooksWrite 들어옴");

		int value = adminBookCUDService.deleteLibraryBooksAndUpdateApproval(lbidx);
		
		if (value==2) {
			return "error"; 
		}	
		return "success"; 
	}
	
	// 도서 수정 페이지 이동
	@GetMapping("/{lbidx}/bookModify.do")
	public String moveBookModify(@PathVariable("lbidx") int lbidx,Model model) {
		logger.debug("moveBookModify 들어옴");
		
		//도서관 책 상세 조회 
		LibraryBooksDto lbd = adminLibraryBooksService.getBookSelectOne(lbidx);
		
	    // 도서 카테고리 대분류
	    List<LibraryBooksDto> parentList = adminCategoryService.getParentCategoryByLevel(); 

	    // 도서 카테고리 소분류
	    List<LibraryBooksDto> childList = adminCategoryService.getChildrenCategoryByparentCode(Integer.parseInt(lbd.getParentCode()));

		model.addAttribute("lbd", lbd);
		model.addAttribute("parentList", parentList);
		model.addAttribute("childList", childList);
		return "admin/book/bookModify";

	}
	
	// 도서 수정 Action 기능
	@PostMapping("/bookModifyAction.do")
	public String modifyLibraryBooks(LibraryBooksVo lbv, RedirectAttributes rttr) {
		logger.debug("bookModifyAction 들어옴");
		
		// 도서관 책 삭제
		int value = adminBookCUDService.modifyLibraryBooksAndUpdateApproval(lbv);
		if (value==0) {
			rttr.addFlashAttribute("msg", "수정하지 못했습니다.");
			return "redirect:/admin/book/"+lbv.getLbidx()+"/bookModify.do"; 
		}	
		return "redirect:/admin/book/"+lbv.getLbidx()+"/bookDetail.do"; 

	}
	
	// 도서 목록 페이지 이동(팝업)
		@ResponseBody
		@RequestMapping(value="/bookList.do", method = RequestMethod.POST)
		public HashMap<String, Object> bookList(
				@RequestParam(value = "searchType", defaultValue = "title") String searchType,
				@RequestParam(value = "keyword", defaultValue = "") String keyword,
				@RequestParam(value = "page", defaultValue = "1") int page
			 ) {
						
			 logger.debug("bookList 들어옴");

			// 현재 페이지 저장
		     SearchCriteria scri = new SearchCriteria();
		     scri.setPage(page);
			 pm.setScri(scri);

		     // API를 위해 사용자가 입력한 검색조건과 검색어 및 현재 페이지 세팅
		     int start = page;
		     String searchWord = keyword;
		     String queryType = searchType;
		     
		 	 HashMap<String, Object> hm = new HashMap<String, Object>();
		 	 List<BooksVo> alist = new ArrayList<>();  // List는 인터페이스이기 때문에 객체 생성을 못함 -> List<>()가 아닌 ArrayList<>()로 초기화
		 	int totalCount = 0;
		 	
		     try {
		    	ApiBookPageDto abpd = aladdinOpenAPI.searchBooksList(searchWord, queryType, start);

		        if (abpd != null && !abpd.getBlist().isEmpty()) {

		            alist = abpd.getBlist();
		            totalCount = abpd.getTotalCount();
		            
		        }

		     } catch (Exception e) {
		        e.printStackTrace();
		     }
		     
	         // 페이징을 위한 전체 데이터 갯수 저장
			 pm.setTotalCount(totalCount);
				
			 hm.put("alist", alist);  // 책 리스트
			 hm.put("pm", pm);  // 페이징 정보
			 
			 return hm;
			
		}

}
