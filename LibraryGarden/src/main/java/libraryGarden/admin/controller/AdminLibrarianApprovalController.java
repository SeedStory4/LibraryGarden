package libraryGarden.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.admin.service.AdminBookRequestService;
import libraryGarden.admin.service.AdminLibrarianApprovalService;
import libraryGarden.cmm.util.UrlEncoder;

@Controller
@RequestMapping(value="/admin/librarianApproval")
public class AdminLibrarianApprovalController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLibrarianApprovalController.class);
	
	@Autowired(required=false)
	private AdminLibrarianApprovalService librarianApprovalService;
	
	@Autowired(required=false)
	private AdminBookRequestService bookRequestService;
	
	@Autowired(required=false)
	private PageMaker pm;
	
	@RequestMapping(value="/librarianApprovalList.do")
	public String librarianApprovalList(
			SearchCriteria scri,
			ApprovalDto ad,
			Model model) {
		 
		 logger.debug("📝 librarianApprovalList 들어옴");

		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 결재 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = ad.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = librarianApprovalService.librarianApprovalTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<ApprovalDto> alist = librarianApprovalService.librarianApprovalSelectAll(scri, filter);

		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("alist", alist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		 return "admin/librarianApproval/librarianApprovalList";
	}

	@RequestMapping(value="/{aidx}/librarianApprovalDetail.do")
	public String librarianApprovalDetail(
			@PathVariable("aidx") int aidx,
			Model model) {
		
		logger.debug("📝 librarianApprovalDetail 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BookVo bv = librarianApprovalService.librarianApprovalSelectOne(aidx);

		// 본인이 작성한 기안인지 확인 및 기안의 상태값 확인하기 위해 DB에서 av 정보 가져오기
		ApprovalVo av = librarianApprovalService.librarianApprovalSelectAv(aidx);	
		
		model.addAttribute("bv", bv);
		model.addAttribute("av", av);
		
		return "admin/librarianApproval/librarianApprovalDetail";
	}

	@PostMapping(value="/{aidx}/librarianApprovalDeleteAction.do")
	public String boardDeleteAction(
			@PathVariable("aidx") int aidx,
			RedirectAttributes rttr) {
		
		logger.info("📝 librarianApprovalDeleteAction 들어옴");		
		
		// 해당 결재 게시글의 delyn 값 Y로 변경하기
		int value = librarianApprovalService.librarianApprovalDelete(aidx);

		// 삭제 후 이동할 url 및 메세지 설정
		String path = "redirect:/admin/librarianApproval/librarianApprovalList.do";
		rttr.addFlashAttribute("msg", "삭제되었습니다.");
		
		// 삭제 실패시 이동할 url 및 메세지 설정
		if(value == 0) {
			path = "redirect:/admin/librarianApprival/" + aidx + "/librarianApprovalDetail.do";
			rttr.addFlashAttribute("msg", "삭제가 실패했습니다.");
		}
		
		return path;
	}

	@RequestMapping(value="/librarianApprovalWrite.do")
	public String boardWrite() {
		
		logger.info("📝 librarianApprovalWrite 들어옴");

		return "admin/librarianApproval/librarianApprovalWrite";
	}

	@PostMapping(value="/librarianApprovalWriteAction.do")
	public String librarianApprovalWriteAction(
			ApprovalVo av,
			HttpServletRequest request,
			RedirectAttributes rttr,
			Model model
			) {
		
		logger.info("📝 librarianApprovalWriteAction 들어옴");
		
		// DB에 작성자 정보를 저장하기 위해 session에 저장된 uidx를 av 안에 세팅
		UserVo user = (UserVo) request.getSession().getAttribute("loginUser");
		int uidx = user.getUidx();
		av.setUidx(uidx);

		// 게시글 등록 쿼리가 성공했는지 확인하기 위해 aidx의 초기값을 세팅.
		int aidx = 0;
		
		// 작성한 게시글 정보를 DB에 저장. 저장이 성공하면 등록된 게시글의 aidx가 aidx에 저장됨.
		if(av.getRqidx() > 0) {
			av.setBidx(0);
			aidx = librarianApprovalService.librarianApprovalInsert(av);
		} else {
			av.setRqidx(0);
			aidx = librarianApprovalService.librarianApprovalInsert(av);
		}
		
		// 이동할 주소 초기화
		String path = "";
		
		if(aidx != 0) {
			// 게시글 등록 후 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글쓰기가 성공했습니다.");
			path = "redirect:/admin/librarianApproval/" + aidx + "/librarianApprovalDetail.do";
		
		} else {
			// 게시글 등록 실패시 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글쓰기가 실패했습니다.");
			path = "redirect:/admin/librarianApproval/librarianApprovalWrite.do";
		}
		
		return path;
	}
	
	// 희망 도서 목록 페이지 팝업
	@ResponseBody
	@RequestMapping(value="/librarianApprovalSelect.do", method = RequestMethod.POST)
	public HashMap<String, Object> librarianApprovalSelect(
			@RequestParam(value = "rqidx") int rqidx
		 ) {
		
		logger.info("📝 librarianApprovalSelect 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BookVo bv = bookRequestService.bookRequestSelectOne(rqidx);
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("rqidx", rqidx);
		hm.put("bv", bv);
		
		return hm;
		
	}
	
	@RequestMapping(value="/{aidx}/librarianApprovalModify.do")
	public String boardModify(
			@PathVariable("aidx") int aidx,
			Model model) {
		
		logger.debug("📝 librarianApprovalModify 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BookVo bv = librarianApprovalService.librarianApprovalSelectOne(aidx);	
		
		model.addAttribute("bv", bv);
		model.addAttribute("aidx", aidx);
		
		return "admin/librarianApproval/librarianApprovalModify";
		
	}
	
	@RequestMapping(value="/{aidx}/librarianApprovalModifyAction.do", method=RequestMethod.POST)
	public String boardModifyAction(
			ApprovalVo av,
			HttpServletRequest request,
			RedirectAttributes rttr
			) {
		
		logger.info("📝 librarianApprovalModifyAction 들어옴");
		
		// 게시글 등록 쿼리가 성공했는지 확인하기 위해 value의 초기값을 세팅.
		int value = 0;
		
		// 수정한 게시글 정보를 DB에 반영. 저장이 성공하면 value가 변경됨.
		if(av.getRqidx() > 0) {
			av.setBidx(0);
			value = librarianApprovalService.librarianApprovalUpdate(av);
		} else {
			av.setRqidx(0);
			value = librarianApprovalService.librarianApprovalUpdate(av);
		}

		// 이동할 주소 초기화
		String path = "";
		
		if(value != 0) {
			// 게시글 등록 후 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글수정이 성공했습니다.");
			path = "redirect:/admin/librarianApproval/" + av.getAidx() + "/librarianApprovalDetail.do";
		
		} else {
			// 게시글 등록 실패시 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글수정이 실패했습니다.");
			path = "redirect:/admin/librarianApproval/" + av.getAidx() + "/librarianApprovalModify.do";
		}
		
		return path;
	}
}
