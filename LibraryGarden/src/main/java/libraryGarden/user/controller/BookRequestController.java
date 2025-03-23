package libraryGarden.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * [설명] 사용자의 희망도서 신청 목록 페이지(신청하기 위해 목록으로 들어옴)
 * 
 * [주요기능]
 * - 희망 도서 신청 목록 페이지 이동
 *  
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/user/bookRequest")
public class BookRequestController {

	// 희망 도서 신청 목록 페이지 이동
	@GetMapping("/bookRequestWrite.do")
	public String myPageRequestList() {
		return "user/bookRequest/bookRequestWrite";
	}
		
}