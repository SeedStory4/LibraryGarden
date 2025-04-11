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
import libraryGarden.admin.service.AdminApprovalService;
import libraryGarden.admin.service.AdminBookRequestService;
import libraryGarden.cmm.util.UrlEncoder;

@Controller
@RequestMapping(value="/admin/approval")
public class AdminApprovalController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminApprovalController.class);
	
	@Autowired(required=false)
	private AdminApprovalService approvalService;
	
	@Autowired(required=false)
	private AdminBookRequestService bookRequestService;
	
	@Autowired(required=false)
	private PageMaker pm;
	
	@RequestMapping(value="/approvalList.do")
	public String approvalList(
			SearchCriteria scri,
			ApprovalDto ad,
			Model model) {
		 
		 logger.debug("📝 approvalList 들어옴");

		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 결재 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = ad.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = approvalService.approvalTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<ApprovalDto> alist = approvalService.approvalSelectAll(scri, filter);

		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("alist", alist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		 return "admin/approval/approvalList";
	}

	@RequestMapping(value="/{aidx}/approvalDetail.do")
	public String approvalDetail(
			@PathVariable("aidx") int aidx,
			Model model) {
		
		logger.debug("📝 approvalDetail 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BookVo bv = approvalService.approvalSelectOne(aidx);

		// 본인이 작성한 기안인지 확인 및 기안의 상태값 확인하기 위해 DB에서 av 정보 가져오기
		ApprovalVo av = approvalService.approvalSelectAv(aidx);	
		
		model.addAttribute("bv", bv);
		model.addAttribute("av", av);
		
		return "admin/approval/approvalDetail";
	}
	
	@PostMapping(value="/{aidx}/approvalDeleteAction.do")
	public String approvalDeleteAction(
			@PathVariable("aidx") int aidx,
			RedirectAttributes rttr) {
		
		logger.info("📝 approvalDeleteAction 들어옴");		
		
		// 희망도서로 등록된 기안 삭제시 희망도서 상태를 신청대기로 변경
		ApprovalVo av = approvalService.approvalSelectAv(aidx);

		// 이동할 주소 초기화
		String path = "";
		
		try {
			// 해당 결재 게시글의 delyn 값 Y로 변경 및 희망도서로 등록한 경우 희망도서의 상태를 "신청대기"로 변경.
			int value = approvalService.approvalDelete(av, "신청대기");
			
			// 삭제 후 이동할 url 및 메세지 설정
			path = "redirect:/admin/approval/approvalList.do";
			rttr.addFlashAttribute("msg", "삭제되었습니다.");
			
		} catch (Exception e) {
			// 삭제 실패시 이동할 url 및 메세지 설정
			path = "redirect:/admin/approval/" + aidx + "/approvalDetail.do";
			rttr.addFlashAttribute("msg", "삭제가 실패했습니다.");
		}

		return path;
	}

	@RequestMapping(value="/approvalWrite.do")
	public String approvalWrite() {
		
		logger.info("📝 approvalWrite 들어옴");

		return "admin/approval/approvalWrite";
	}
		
	@PostMapping(value="/approvalWriteAction.do")
	public String approvalWriteAction(
			ApprovalVo av,
			HttpServletRequest request,
			RedirectAttributes rttr,
			Model model
			) {
		
		logger.info("📝 approvalWriteAction 들어옴");
		
		// DB에 작성자 정보를 저장하기 위해 session에 저장된 uidx를 av 안에 세팅
		UserVo user = (UserVo) request.getSession().getAttribute("loginUser");
		int uidx = user.getUidx();
		av.setUidx(uidx);

		// 이동할 주소 초기화
		String path = "";
		
		try {
			// 작성한 게시글 정보를 DB에 저장 및 희망도서로 등록한 경우 희망도서의 상태를 "신청중"으로 변경. 저장이 성공하면 등록된 게시글의 aidx가 aidx에 저장됨.
			int aidx = approvalService.approvalInsert(av, "신청중");
			
			// 게시글 등록 후 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글쓰기가 성공했습니다.");
			path = "redirect:/admin/approval/" + aidx + "/approvalDetail.do";
			
		} catch (Exception e) {
			// 게시글 등록 실패시 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글쓰기가 실패했습니다.");
			path = "redirect:/admin/approval/approvalWrite.do";
		}
		
		return path;
	}
	
	// 희망 도서 목록 페이지 팝업
	@ResponseBody
	@RequestMapping(value="/approvalSelect.do", method = RequestMethod.POST)
	public HashMap<String, Object> approvalSelect(
			@RequestParam(value = "rqidx") int rqidx
		 ) {
		
		logger.info("📝 approvalSelect 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BookVo bv = bookRequestService.bookRequestSelectOne(rqidx);
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("rqidx", rqidx);
		hm.put("bv", bv);
		
		return hm;
		
	}
	
	@RequestMapping(value="/{aidx}/approvalModify.do")
	public String approvalModify(
			@PathVariable("aidx") int aidx,
			Model model) {
		
		logger.debug("📝 approvalModify 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BookVo bv = approvalService.approvalSelectOne(aidx);	
		
		model.addAttribute("bv", bv);
		model.addAttribute("aidx", aidx);
		
		return "admin/approval/approvalModify";
		
	}
	
	@RequestMapping(value="/{aidx}/approvalModifyAction.do", method=RequestMethod.POST)
	public String approvalModifyAction(
			ApprovalVo av,
			HttpServletRequest request,
			RedirectAttributes rttr
			) {
		
		logger.info("📝 approvalModifyAction 들어옴");

		// 이동할 주소 초기화
		String path = "";
		
		try {
			// 수정한 게시글 정보를 DB에 저장 및 기존에 등록된 도서가 희망도서로 등록한 경우 희망도서의 상태를 "신청대기"로 변경.
			// 변경한 도서가 희망도서로 등록한 경우 희망도서의 상태를 "신청중"으로 변경.
			int value = approvalService.approvalUpdate(av);
			
			// 게시글 수정 후 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글수정이 성공했습니다.");
			path = "redirect:/admin/approval/" + av.getAidx() + "/approvalDetail.do";
			
		} catch (Exception e) {
			// 게시글 수정 실패시 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "글수정이 실패했습니다.");
			path = "redirect:/admin/approval/" + av.getAidx() + "/approvalModify.do";
		}
				
		return path;
	}
}
