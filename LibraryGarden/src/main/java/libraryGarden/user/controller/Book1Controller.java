package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * [설명] 사용자의 도서 조회 관련 요청을 처리하는 컨트롤러
 * 
 * [주요기능]
 * - 도서 조회 목록 페이지 이동
 * - 도서 조회 검색 기능
 * - 도서 조회 페이지 기능
 * 
 * 
 *  - 도서 조회 상세 페이지 이동
 *  
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/user/book")
public class Book1Controller {

	// 도서 조회 목록 이동
	@GetMapping("/bookList.do")
	public String bookList() {
		return "user/book/bookList";
	}
	
	
	
	// 도서 조회 상세 이동
	@GetMapping("/bookDetail.do")
	public String bookDetail() {
		return "user/book/bookDetail";
	}
	
}