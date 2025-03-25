package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * [설명] 관리자의 도서 관리 페이지
 * 
 * [주요기능]
 * - 관리자 도서관 도서 목록 페이지 이동(이하 도서 목록 페이지 이동)
 * 
 * - 관리자 도서관 도서 상세 페이지 이동(이하 도서 상세 페이지 이동)
 * 
 * - 관리자 도서관 도서 수정 페이지 이동(이하 도서 수정 페이지 이동)
 * 
 * - 관리자 도서관 도서 등록 페이지 이동(이하 도서 등록 페이지 이동)
 * 
 * - 관리자 도서 선택 팝업 페이지 이동(도서 등록할 때 사용)(이하 도서 선택 팝업 페이지 이동)
 *  
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/admin/book")
public class AdminBookController {
	
	// 도서 목록 페이지 이동
	@GetMapping("/bookList.do")
	public String bookList() {
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
