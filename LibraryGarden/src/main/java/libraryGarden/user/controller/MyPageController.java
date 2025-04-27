package libraryGarden.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.user.service.BookLoanService;
import libraryGarden.user.service.BookRequestService;
import libraryGarden.user.service.BookReservationService;
import libraryGarden.user.service.UserService;

@Controller
@RequestMapping("/user/myPage")
public class MyPageController {
	
    @Autowired
    private BookLoanService bookLoanService;
    
    @Autowired
    private BookReservationService bookReservationService;
    
	// BookRequestService 주입
	@Autowired(required=false)
	BookRequestService bookRequestService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
    
	@Autowired(required=false)
	private PageMaker pm;
	
	private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);
	
	
    // 내 도서 대출 목록 화면
	@GetMapping("/myPageLoanList.do")
	public String myPageLoanList(
	    @RequestParam(value = "page", defaultValue = "1") int page,
	    HttpSession session,
	    Model model
	) throws Exception {
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");

	    String userNumber = loginUser.getUserNumber();
	    String name = loginUser.getName();

	    String loanStatus = bookLoanService.getUserLoanStatus(userNumber);
	    model.addAttribute("loanStatus", loanStatus);

	    int perPageNum = 12;
	    Map<String, Object> loanList = bookLoanService.getUserLoanInfo(userNumber, page, perPageNum);
	    model.addAttribute("loanList", loanList);

	    int totalCount = (int) loanList.get("totalCount");
	    int totalPageCount = (int) Math.ceil((double) totalCount / perPageNum);

	    model.addAttribute("totalPageCount", totalPageCount);
	    model.addAttribute("currentPage", page); // 현재 페이지 정보도 전달
	    model.addAttribute("pageSize", perPageNum);
	    model.addAttribute("name", name);
	    model.addAttribute("userNumber", userNumber);

	    return "user/myPage/myPageLoanList";
	}
       
	
	// 내 도서 
	@GetMapping("/myPageReservationList.do")
	public String myPageReservationList(
	    @RequestParam(value = "page", defaultValue = "1") int page,
	    HttpSession session,
	    Model model
	) throws Exception {
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        return "redirect:/user/user/userLogin.do";
	    }

	    String userNumber = loginUser.getUserNumber();
	    String name = loginUser.getName();

	    String loanStatus = bookLoanService.getUserLoanStatus(userNumber);
	    model.addAttribute("loanStatus", loanStatus);

	    int perPageNum = 12;
	    Map<String, Object> reservationList = bookReservationService.getUserReservationInfo(userNumber, page, perPageNum);
	    model.addAttribute("reservationList", reservationList);

	    int totalCount = (int) reservationList.get("totalCount");
	    int totalPageCount = (int) Math.ceil((double) totalCount / perPageNum);

	    model.addAttribute("totalPageCount", totalPageCount);
	    model.addAttribute("currentPage", page); // 현재 페이지 정보도 전달
	    model.addAttribute("pageSize", perPageNum);
	    model.addAttribute("name", name);
	    model.addAttribute("userNumber", userNumber);

	    return "user/myPage/myPageReservationList";
	}
	
    // 연장 요청 처리 (대출 연장)
    @ResponseBody
    @PostMapping("/extendLoan.do")
    public Map<String, Object> extendLoan(
            @RequestParam("lidx") int lidx,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        UserVo loginUser = (UserVo) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("success", false);
            result.put("message", "로그인 후 이용해주세요.");
            return result;
        }
        String userNumber = loginUser.getUserNumber();
        try {
            boolean success = bookLoanService.extendLoan(lidx, userNumber);
            if (success) {
                result.put("success", true);
                result.put("message", "연장이 완료되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "연장이 불가능합니다. 대출현황 또는 예약현황을 확인해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "연장 처리 중 오류가 발생했습니다.");
        }
        return result;
    }
    
	// 도서신청관리(희망 도서 신청한 목록) 페이지 이동
	@GetMapping("/myPageRequestList.do")
	public String myPageRequestList(SearchCriteria scri, HttpSession session, Model model) throws Exception {
		logger.debug("MyPageController myPageRequestList 들어옴");
		
		// 로그인 확인
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/user/user/userLogin.do"; // 로그인 안 되어 있으면 로그인 페이지로
	    }
		
		// 페이징
		if (scri.getPage() == 0) {
		    scri.setPage(1); // 기본 1페이지로
		}
		if (scri.getPerPageNum() == 0) {
		    scri.setPerPageNum(12); // ⭐ perPageNum도 기본 12개로 세팅
		}
		
		pm.setScri(scri);
		int cnt = bookRequestService.getUserRequestInfoTotalCount(loginUser.getUidx()); // 전체 수
	    pm.setTotalCount(cnt);
		 
		// 로그인 한 유저 대출상태 확인
		String loanStatus = bookLoanService.getUserLoanStatus(loginUser.getUserNumber()); 
	    
		// 유저의 희망도서 신청 리스트 출력
		List<Map<String, Object>> requestList = bookRequestService.getUserRequestInfo(loginUser.getUidx(), scri);
	    
	    model.addAttribute("uv", loginUser);// 유저 정보
	    model.addAttribute("loanStatus", loanStatus); // 대출가능 정보
	    model.addAttribute("requestList", requestList); // 희망도서 신청 정보
	    model.addAttribute("pm", pm); // 페이징
	    
		return "user/myPage/myPageRequestList";
	}

	// 신청한 희망 도서 삭제
	@PostMapping("/deleteRequest.do")
	public String deleteRequest(@RequestParam(value = "page", required = false, defaultValue = "1") int  page, @RequestParam("bidx") int bidx, @RequestParam("rqidx") int rqidx, HttpSession session, Model model) throws Exception {
		logger.debug("MyPageController deleteRequest 들어옴");
		
		// 로그인 정보 세션값에서 가지고 오기
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/user/user/userLogin.do"; // 로그인 안 되어 있으면 로그인 페이지로
	    }
		
		// 삭제하고 값 가지고 오기
		int value = bookRequestService.deleteRequest(loginUser.getUidx(),bidx,rqidx);
		
		if(value == 0) {
			model.addAttribute("msg", "희망도서 삭제를 실패했습니다. 잠시 후 다시 시도해주세요.");
			return "redirect:/user/myPage/myPageRequestList.do?page="+page;
		}
		return "redirect:/user/myPage/myPageRequestList.do?page="+page;
	}
	
	// 내 도서 대출이력관리
	@GetMapping("/myPageModify.do")
	public String myPageModify(HttpSession session, Model model) {
		
		UserVo loginUser = (UserVo) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/user/user/userLogin.do"; // 로그인 안 되어 있으면 로그인 페이지로
	    }
		// DB에서 최신 정보 가져오기 (비밀번호 제외)
	    UserVo userInfo = userService.selectUserById(loginUser.getId());
	    model.addAttribute("user", userInfo);
			return "user/myPage/myPageModify";
	}
	
	@PostMapping("/userUpdate.do")
	public String updateUser(
	    @RequestParam("NowPassword") String nowPassword,
	    @RequestParam(value = "password", required = false) String password,
	    @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
	    @RequestParam("phone") String phone,
	    @RequestParam("email") String email,
	    @RequestParam("address") String address,
	    HttpSession session,
	    RedirectAttributes rttr
	) {
	    UserVo loginUser = (UserVo) session.getAttribute("loginUser");

	    // 현재 비밀번호 확인
	    if (!passwordEncoder.matches(nowPassword, loginUser.getPassword())) {
	        rttr.addFlashAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
	        return "redirect:/user/myPage/myPageModify.do";
	    }

	    // 기본값: 기존 비밀번호
	    String newPassword = loginUser.getPassword();		

	    // 새 비밀번호 입력된 경우에만 변경
	    if (password != null && !password.trim().isEmpty()) {
	        if (!password.equals(passwordConfirm)) {
	            rttr.addFlashAttribute("error", "새 비밀번호가 일치하지 않습니다.");
	            return "redirect:/user/myPage/myPageModify.do";
	        }
	        newPassword = passwordEncoder.encode(password);
	    }

	    // 업데이트할 정보 구성
	    UserVo updateUser = new UserVo();
	    updateUser.setId(loginUser.getId());
	    updateUser.setPassword(newPassword);
	    updateUser.setPhone(phone);
	    updateUser.setEmail(email);
	    updateUser.setAddress(address);

	    userService.updateUser(updateUser);

	    // 세션 정보도 최신화
	    loginUser.setPassword(newPassword);
	    loginUser.setPhone(phone);
	    loginUser.setEmail(email);
	    loginUser.setAddress(address);
	    
	    

	    if (password != null && !password.trim().isEmpty()) {
	        // 비밀번호 변경 처리...
	        rttr.addFlashAttribute("logoutMsg", "비밀번호가 변경되었습니다. 변경된 비밀번호로 다시 로그인해주세요.");
	    } else {
	        rttr.addFlashAttribute("logoutMsg", "회원정보가 성공적으로 수정되었습니다.");
	    }
	    // 로그아웃: 세션 초기화
	    session.invalidate();

	    return "redirect:/user/user/userLogin.do";
	}
    
    
}
	
	
	

