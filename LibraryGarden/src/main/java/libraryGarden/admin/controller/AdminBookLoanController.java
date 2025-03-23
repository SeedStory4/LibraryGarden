package libraryGarden.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.admin.service.AdminBookLoanService;

@Controller
@RequestMapping("/admin/bookLoan")
public class AdminBookLoanController {
	
    @Autowired(required=false)
    private AdminBookLoanService adminBookLoanService;

	// 도서 대여 화면
	@GetMapping("/bookLoanWrite.do")
	public String bookLoanWrite() {
		//System.out.println("bookLoanWrite.do 호출됨");
		return "admin/bookLoan/bookLoanWrite";
	}
	
    // 회원번호로 대출 가능 여부와 대출 목록 조회 (AJAX)
    @GetMapping(value="/checkUserLoanStatus.do")
    @ResponseBody
    public Map<String, Object> checkUserLoanStatus(
            @RequestParam("userNumber") String userNumber,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPageNum", defaultValue = "12") int perPageNum) {
        Map<String, Object> result = adminBookLoanService.getUserLoanInfo(userNumber, page, perPageNum);
        return result;
    }
    
    @PostMapping("/addBookLoan.do")
    public ResponseEntity<String> addBookLoan(@RequestParam String userNumber, @RequestParam String code) {
        try {
            // 연체 상태 확인
            if (adminBookLoanService.isUserOverdue(userNumber)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("연체 중인 회원입니다. 대출할 수 없습니다.");
            }

            // 대여 등록 로직
            adminBookLoanService.addBookLoan(userNumber, code);
            return ResponseEntity.ok("대여 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대여 등록 실패: " + e.getMessage());
        }
    }


    @PostMapping("/checkBookStatus.do")
    public ResponseEntity<String> checkBookStatus(@RequestParam String code) {
        try {
            String status = adminBookLoanService.getBookStatus(code);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("도서 상태 조회 실패: " + e.getMessage());
        }
    }

}
