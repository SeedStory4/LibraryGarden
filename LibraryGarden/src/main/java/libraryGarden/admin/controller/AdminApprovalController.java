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
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;
import libraryGarden.admin.service.AdminApprovalService;
import libraryGarden.admin.service.AdminBookRequestService;
import libraryGarden.cmm.util.AladdinOpenAPI;
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
	
	@Autowired(required = false)
	AladdinOpenAPI aladdinOpenAPI;
	
	@RequestMapping(value="/approvalList.do")
	public String getApprovalList(
			SearchCriteria scri,
			ApprovalDto ad,
			Model model) {
		 
		 logger.debug("📝 getApprovalList 들어옴");

		 // 사용자가 입력한 검색조건과 검색어 저장
		 pm.setScri(scri);

		 // 결재 상태별로 데이터를 보여주기 위해서 현재 탭의 결재 상태 저장
		 String filter = ad.getStatus();
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = approvalService.getApprovalTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<ApprovalDto> alist = approvalService.getApprovalSelectAll(scri, filter);

		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 model.addAttribute("alist", alist);
		 model.addAttribute("pm", pm);
		 model.addAttribute("filter", filter);
		
		 return "admin/approval/approvalList";
	}

	@RequestMapping(value="/{aidx}/approvalDetail.do")
	public String getApprovalDetail(
			@PathVariable("aidx") int aidx,
			Model model) {
		
		logger.debug("📝 getApprovalDetail 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BooksVo bv = approvalService.getApprovalSelectOne(aidx);

		// 본인이 작성한 기안인지 확인 및 기안의 상태값 확인하기 위해 DB에서 av 정보 가져오기
		ApprovalVo av = approvalService.getApprovalSelectAv(aidx);	
		
		model.addAttribute("bv", bv);
		model.addAttribute("av", av);
		
		return "admin/approval/approvalDetail";
	}
	
	@PostMapping(value="/{aidx}/approvalDeleteAction.do")
	public String deleteApproval(
			@PathVariable("aidx") int aidx,
			RedirectAttributes rttr) {
		
		logger.debug("📝 deleteApproval 들어옴");
		
		// aidx로 ApprovalVo 가져오기
		ApprovalVo av = approvalService.getApprovalSelectAv(aidx);

		// 이동할 주소 초기화
		String path = "";
		
		try {
			// 해당 결재 게시글의 delyn 값 Y로 변경 및 희망도서로 등록한 경우 희망도서의 상태를 "신청대기"로 변경.
			int value = approvalService.deleteApproval(av, "신청대기");
			
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
	public String writeApproval() {
		
		logger.debug("📝 writeApproval 들어옴");

		return "admin/approval/approvalWrite";
	}
		
	@PostMapping(value="/approvalWriteAction.do")
	public String insertApproval(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "num") String num,
			HttpServletRequest request,
			RedirectAttributes rttr,
			Model model
			) {
		
		logger.debug("📝 insertApproval 들어옴");
		
		ApprovalVo av = new ApprovalVo();
		BooksVo bv = new BooksVo();
		
		// DB에 작성자 정보를 저장하기 위해 session에 저장된 uidx를 av 안에 세팅
		UserVo user = (UserVo) request.getSession().getAttribute("loginUser");
		int uidx = user.getUidx();
		av.setUidx(uidx);

		// 이동할 주소 초기화
		String path = "";
		
		if(type.equals("rqidx")) {
			av.setRqidx(Integer.parseInt(num));
		} else {
			bv.setIsbn(num);
		}

		try {

			// 작성한 게시글 정보를 DB에 저장 및 희망도서로 등록한 경우 희망도서의 상태를 "신청중"으로 변경. 저장이 성공하면 등록된 게시글의 aidx가 aidx에 저장됨.
			int aidx = approvalService.insertApproval(av, bv, "신청중");
			
			// 게시글 등록 후 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "기안 등록이 성공했습니다.");
			path = "redirect:/admin/approval/" + aidx + "/approvalDetail.do";
			
		} catch (Exception e) {
			// 게시글 등록 실패시 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "기안 등록이 실패했습니다.");
			path = "redirect:/admin/approval/approvalWrite.do";
		}
		
		return path;
	}
	
	// 희망 도서 목록 페이지 팝업
	@ResponseBody
	@RequestMapping(value="/approvalSelect.do", method = RequestMethod.POST)
	public HashMap<String, Object> SelectApproval(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "num") String num
		 ) {
		
		logger.debug("📝 SelectApproval 들어옴");
		
		BooksVo bv = null;
		
		if(type.equals("rqidx")) {
			// 도서 정보 DB에서 가져오기
			bv = bookRequestService.getBookRequestSelectOne(Integer.parseInt(num));
		} else {
			// 도서 정보 알라딘 API에서 가져오기
		    try {
				bv = aladdinOpenAPI.lookUpBookDetail(num);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("num", num);
		hm.put("bv", bv);
		
		return hm;
		
	}
	
	@RequestMapping(value="/{aidx}/approvalModify.do")
	public String modifyApproval(
			@PathVariable("aidx") int aidx,
			Model model) {
		
		logger.debug("📝 modifyApproval 들어옴");
		
		// 도서 정보 DB에서 가져오기
		BooksVo bv = approvalService.getApprovalSelectOne(aidx);
		
		model.addAttribute("bv", bv);
		model.addAttribute("aidx", aidx);
		
		return "admin/approval/approvalModify";
		
	}
	
	@RequestMapping(value="/{aidx}/approvalModifyAction.do", method=RequestMethod.POST)
	public String updateApproval(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "num") String num,
			@PathVariable("aidx") int aidx,
			HttpServletRequest request,
			RedirectAttributes rttr
			) {
		
		logger.debug("📝 updateApproval 들어옴");

		ApprovalVo av = new ApprovalVo();
		BooksVo bv = new BooksVo();
		
		// 해당 기안 수정을 위해 aidx를 av 안에 세팅
		av.setAidx(aidx);

		// 이동할 주소 초기화
		String path = "";
		
		if(type.equals("rqidx")) {
			av.setRqidx(Integer.parseInt(num));
		} else {
			bv.setIsbn(num);
		}
		
		try {
			// 수정한 게시글 정보를 DB에 저장 및 기존에 등록된 도서가 희망도서로 등록한 경우 희망도서의 상태를 "신청대기"로 변경.
			// 변경한 도서가 희망도서로 등록한 경우 희망도서의 상태를 "신청중"으로 변경.
			int value = approvalService.updateApproval(av, bv);
			
			// 게시글 수정 후 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "기안 수정이 성공했습니다.");
			path = "redirect:/admin/approval/" + av.getAidx() + "/approvalDetail.do";
			
		} catch (Exception e) {
			// 게시글 수정 실패시 이동할 url 및 메세지 설정
			rttr.addFlashAttribute("msg", "기안 수정이 실패했습니다.");
			path = "redirect:/admin/approval/" + av.getAidx() + "/approvalModify.do";
		}
				
		return path;
	}
	
	// 기안 반려/승인
	@ResponseBody
	@RequestMapping(value="/{aidx}/approvalProcessingAction.do", method=RequestMethod.POST)
	public HashMap<String, Object> updateApprovalProcessing(
			ApprovalVo av,
			@PathVariable("aidx") int aidx
			) {
		
		logger.debug("📝 updateApprovalProcessing 들어옴");
		
		// 해당 기안 반려를 위해 aidx를 av 안에 세팅
		av.setAidx(aidx);
		
		// 기안 반려시 기안 상태를 "신청반려"로 변경 및 반려 사유 등록, 기안 승인시 기안 상태를 "신청완료"로 변경
		int value = approvalService.updateApprovalProcessing(av);
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("rejectionReason", av.getRejectionReason());
		
		return hm;
	}
}
