package libraryGarden.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.cmm.util.AladdinOpenAPI;
import libraryGarden.domain.ApiBookPageDto;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.user.service.LibraryBookService;
import libraryGarden.user.service.Book1Service;
import libraryGarden.user.service.BookRequestService;

/**
 * [설명] 사용자의 희망도서 신청 목록 페이지(신청하기 위해 목록으로 들어옴)
 * 
 * [주요기능]
 * - 희망 도서 신청 목록 페이지 이동
 * - 희망 도서 신청 등록
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/user/bookRequest")
public class BookRequestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookRequestController.class);

	
	// AladdinOpenAPI 주입
	@Autowired(required = false)
	AladdinOpenAPI aladdinOpenAPI;

	// PageMaker 주입 (페이징 기능)
	@Autowired(required=false)
	private PageMaker pm;
	
	// BookRequestService 주입
	@Autowired(required=false)
	BookRequestService bookRequestService;
	
	// BookRequestService 주입
	@Autowired(required=false)
	Book1Service bookService;
	
	// 희망 도서 신청 목록 페이지 이동
	@GetMapping("/bookRequestWrite.do")
	public String bookRequestWrite( SearchCriteria scri, Model model, HttpSession session) throws Exception {
		logger.debug("BookRequestController bookRequestWrite 들어옴");
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/user/user/userLogin.do";
	    }

	    // 페이징 처리 준비
	    int page = scri.getPage();
	    int start = page; // API 요청 시작 인덱스
	    String searchWord = scri.getKeyword();
	    String queryType = scri.getSearchType();


		// 검색어 필수 검증
	    if(searchWord == null){
	    	return "user/bookRequest/bookRequestWrite";
	    }
	    
	    if (searchWord.trim().isEmpty()) {
	        model.addAttribute("msg", "검색어를 입력해주세요.");
	        return "user/bookRequest/bookRequestWrite";
	    }

	    ApiBookPageDto abpd = null;
	    try {
	    	abpd = aladdinOpenAPI.searchBooksList(searchWord, queryType, start);

	        if (abpd == null || abpd.getBlist().isEmpty()) {
	            model.addAttribute("msg", "검색 결과가 없습니다. 다른 키워드를 입력해주세요.");
	        } else {
	            List<BookVo> blist = abpd.getBlist();
	            int totalCount = abpd.getTotalCount();

		   		pm.setScri(scri);
				pm.setTotalCount(totalCount);
				
	            model.addAttribute("blist", blist);       // 책 리스트
	            model.addAttribute("pm", pm);				  // 페이징 정보
	        }

	    } catch (Exception e) {
	        e.printStackTrace(); // 개발자용 로그
	        model.addAttribute("msg", "서비스에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
	    }
				
		return "user/bookRequest/bookRequestWrite";
	}
	
	// 희망 도서 신청 등록
	@PostMapping("/bookRequestWriteAction.do")
	@ResponseBody
	public String bookRequestWriteAction(@RequestBody Map<String, Object> requestData, HttpSession session) throws Exception {
		logger.debug("BookRequestController bookRequestWriteAction 들어옴");
		
		/***변수 선언***/
		int bidx = 0; // bidx를 담을 값
		int bookInsertValue = 0;
		int requestInsertValue = 0;
		/***********/
		
	    // 로그인한 사용자 정보 - 회원 고유 번호 뽑아오기
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");
	    int uidx = loginUser.getUidx();  // 회원 고유 번호
	    logger.debug("BookRequestController bookRequestWriteAction uidx"+uidx);
	    
	    // 넘어온 isbn
	    String isbn =  String.valueOf(requestData.get("isbn"));
	    logger.debug("BookRequestController bookRequestWriteAction isbn "+isbn);
		
	    // 도서 상세 조회
	    BookVo bv = aladdinOpenAPI.lookUpBookDetail(isbn);
	    
	    // isbn으로 책 여부 확인
	    int cnt = bookService.findBookByIsbnToCount(isbn);
	    logger.debug("BookRequestController bookRequestWriteAction cnt "+cnt);
	    
	    // db Book 테이블에 책이 없고 새로 등록할때
	    if (cnt == 0) {
	        // 책이 없으면 insert
	    	bookInsertValue = bookService.insertBook(bv);
	    	
	    	if(bookInsertValue != 0) { // 책 등록 성공
	    		logger.debug("책 등록 성공");
	    		
		        // 새로 등록한 책의 bidx 가져오기
		        bidx = bookService.findBookByIsbnToBidx(isbn);
		        logger.debug("BookRequestController 책 없을 때 책 등록 성공 bidx "+bidx);
		        
		        // REQUEST 테이블에 희망도서 신청 등록
		        requestInsertValue = bookRequestService.insertRequest(uidx, bidx);
		    	
		        if(requestInsertValue != 0) { // 희망도서 신청 성공
		        	logger.debug("책 없을 때 희망도서 신청 성공");
		    	}else {
		    		logger.debug("책 없을 때 희망도서 신청 실패");
		    	}
	    	}else {
	    		logger.debug("책 등록 실패");
	    	}

	    }
	    // db Book 테이블에 책이 있을 때
	    else {
	    	// 신청한 책의 bidx 가져오기
	        bidx = bookService.findBookByIsbnToBidx(isbn);
	        logger.debug("BookRequestController db Book 테이블에 책이 있을 때 bidx "+bidx);

	        // REQUEST 테이블에 희망도서 신청 등록
	        requestInsertValue = bookRequestService.insertRequest(uidx, bidx);
	    	if(requestInsertValue != 0) { // 희망도서 신청 성공
	    		logger.debug("db Book 테이블에 책이 있을 때 희망도서 신청 성공");
	    	}else {
	    		logger.debug("db Book 테이블에 책이 있을 때 희망도서 신청 실패");
	    	}
	    }

	    return "success";  // 성공하면 "success" 문자열 리턴
	}
		
}