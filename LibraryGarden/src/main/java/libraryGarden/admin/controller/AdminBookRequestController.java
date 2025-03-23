package libraryGarden.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * [설명] 관리자의 희망 도서 관리 페이지
 * 
 * [주요기능]
 * - 관리자 희망 도서 목록 페이지 이동(이하 희망 도서 목록 페이지 이동)
 * 
 * - 관리자 희망 도서 상세 페이지 이동(이하 희망 도서 상세 페이지 이동)
 * 
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */
@Controller
@RequestMapping("/admin/bookRequest")
public class AdminBookRequestController {
	
	// 희망 도서 목록 페이지 이동
	@GetMapping("/bookRequestList.do")
	public String bookRequestList() {
		return "admin/bookRequest/bookRequestList";
	}
	
	// 희망 도서 상세 페이지 이동
	@GetMapping("/bookRequestDetail.do")
	public String bookRequestDetail() {
		return "admin/bookRequest/bookRequestDetail";
	}
   
}
