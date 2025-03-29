package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.admin.service.AdminBookService;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.controller.Book1Controller;
import libraryGarden.user.service.Book1Service;

/**
 * [설명] 관리자의 도서 관리 페이지
 * 
 * [주요기능]
 * - 관리자 도서관 도서 조회 목록 페이지
 * 	- 도서 조회 목록 출력 기능
 * 	- 도서 조회 검색 기능
 * 	- 도서 조회 페이지 기능
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
	
	// AdminBookService 주입
	@Autowired(required=false)
	AdminBookService adminBookService;
	
	// PageMaker 주입 (페이징 기능)
	@Autowired(required=false)
	private PageMaker pm;
	
	// 도서 조회 목록 페이지 이동
	@GetMapping("/bookList.do")
	public String bookList(SearchCriteria scri,Model model) {
		 /* 검색 기능
		  * - 사용자가 입력한 검색조건과 검색어 저장
		  * [input] 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri)
		  */ 
		 pm.setScri(scri);
		 
		 /* 페이징 기능 
		  * - 책 리스트 전체 갯수
		  * - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		  * [input]	 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri) 
		  * [output] 조건에 따른 잭 전체 개수 cnt
		  */ 
		 int cnt = adminBookService.BookTotalCount(scri);
		 
		 /* 페이지 기능
		  * [input] 조건에 따른 책 전체 개수 cnt
		  */
		 pm.setTotalCount(cnt);
		 
		/* 책 목록 조회
		 * [input]	검색조건 외 페이지 기능 (scri)
		 * [output] 책 목록(alist)
		 */ 
		ArrayList<LibraryBookDto> lblist = adminBookService.BookSelectAll(scri);
		
		/* Model를 통해 jsp로 이동
		 * - lblist : 책 목록 리스트
		 * - pm : 페이징 기능
		 */
		model.addAttribute("lblist",lblist);
		model.addAttribute("pm", pm);
		return "admin/book/bookList";
	}
	
	// 도서 상세 페이지 이동
	@GetMapping("/bookDetail.do")
	public String bookDetail() {
		return "admin/book/bookDetail";
	}
	
	// 도서 수정 페이지 이동
	@GetMapping("/bookModify.do")
	public String bookModify() {
		return "admin/book/bookModify";
	}
	
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
}
