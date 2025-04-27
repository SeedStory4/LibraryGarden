package libraryGarden.user.controller;

 
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.user.service.LibraryBooksService;

/**
 * [설명] 사용자의 도서 조회 관련 요청을 처리하는 컨트롤러 / webapp - user - book
 * 
 * [주요기능]
 *  - 도서 조회 목록 페이지 이동
 *  - 도서 조회 상세 페이지 이동
 *  
 * @author SiYeon
 */
@Controller
@RequestMapping("/user/book")
public class Book1Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Book1Controller.class);
	
	// book1Service 주입
	@Autowired(required=false)
	LibraryBooksService libraryBookService;
	
	// PageMaker 주입 (페이징 기능)
	@Autowired(required=false)
	private PageMaker pm;
	
	// 도서 조회 목록 페이지 이동
	@GetMapping("/bookList.do")
	public String bookList(SearchCriteria scri,Model model) {
		 // 검색 기능
		 pm.setScri(scri);
		 
		 // 페이징 기능 
		 int cnt = libraryBookService.getBookTotalCount(scri);
		 
		 // 페이지 기능 
		 pm.setTotalCount(cnt);
		 
		 // 책 목록 조회  
		 ArrayList<LibraryBooksDto> lblist = libraryBookService.getBookSelectAll(scri);
 
	     model.addAttribute("lblist",lblist);
		 model.addAttribute("pm", pm);
		
		 return "user/book/bookList";
	}
	
	// 도서 조회 상세 페이지 이동
	@GetMapping("/{lbidx}/bookDetail.do")
	public String bookDetail(
			@PathVariable("lbidx") int lbidx,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		logger.debug("bookDetail 들어옴");
		
		request.getSession().setAttribute("saveUrl", request.getRequestURI());
		
	    // 로그인 유저가 없어도 상세보기는 가능해야 하니까 체크 안함 
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");
	    String userNumber = loginUser != null ? loginUser.getUserNumber() : "";
		
	    // 도서관 책 상세 조회
		LibraryBooksDto lbd = libraryBookService.getBookSelectOne(lbidx);
		
		model.addAttribute("lbd", lbd);
		model.addAttribute("userNumber", userNumber);
		return "user/book/bookDetail";
	}

}