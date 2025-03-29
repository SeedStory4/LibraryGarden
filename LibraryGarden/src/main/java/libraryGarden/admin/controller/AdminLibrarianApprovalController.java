package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.admin.service.AdminLibrarianApprovalService;
import libraryGarden.cmm.util.UrlEncoder;

@Controller
@RequestMapping(value="/admin/librarianApproval")
public class AdminLibrarianApprovalController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLibrarianApprovalController.class);
	
	@Autowired(required=false)
	private AdminLibrarianApprovalService librarianApprovalService;
	
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
		
		model.addAttribute("bv", bv);
		
		return "admin/librarianApproval/librarianApprovalDetail";
	}
	
	

//	@RequestMapping(value="/{boardcode}/{period}/boardWrite.do")
//	public String boardWrite(
//			@PathVariable("boardcode") String boardcode,
//			@PathVariable("period") int period,
//			Model model) {
//		
//		logger.info("boardWrite����");
//
//		String menu = "";
//		String path = "";
//		if(boardcode.equals("travel")) {
//			if(period == 1) {
//				menu = "����ġ��";
//			} else if(period == 2) {
//				menu = "1��2��";
//			} else if(period == 3) {
//				menu = "2��3��";
//			} else if(period == 4) {
//				menu = "3��4��";
//			}
//			path = "WEB-INF/board/travelWrite";
//		} else if(boardcode.equals("free")) {
//			menu = "�����Խ���";
//			path = "WEB-INF/board/boardWrite";
//		} else if(boardcode.equals("notice")){
//			menu = "��������";
//			path = "WEB-INF/board/boardWrite";
//		}
//
//		model.addAttribute("menu", menu);
//		model.addAttribute("boardcode", boardcode);
//		model.addAttribute("period", period);
//		
//		return path;
//	}
//
//	@RequestMapping(value="/{boardcode}/{period}/boardWriteAction.do", method=RequestMethod.POST)
//	public String boardWriteAction(
//			@PathVariable("boardcode") String boardcode,
//			@PathVariable("period") int period,
//			BoardVo bv,
//			@RequestParam("attachfile") MultipartFile filename,  // input�� name �̸��� BoardVo�� �ִ� ������Ƽ �̸��� �����ϸ� BoardVo�� ���� �Ѿ�� @RequestParam���� ���� �� �����Ƿ�, input�� name�� filename�� �ƴ� attachfile���� �Ѵ�.
//			HttpServletRequest request,
//			RedirectAttributes rttr,
//			Model model,
//			@RequestPart(name = "posterImages", required = false) MultipartFile uploadPosterImages
//			) throws Exception {
//		
//		logger.info("boardWriteAction����");
//		
//		// ����÷��(�����)
//		MultipartFile file = filename;
//		String uploadedFileName = "";
//		
//		if(!file.getOriginalFilename().equals("")) {
//			String uploadPath = "D:\\dev\\myprj\\myprjSpring\\myprj\\src\\main\\webapp\\resources\\boardImages\\";
//			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
//		}
//		
//		String midx = request.getSession().getAttribute("midx").toString();  // HttpSession�� HttpServletRequest �ȿ� ����
//		int midx_int = Integer.parseInt(midx);
//		bv.setMidx(midx_int);
//		
//		String ip = userip.getUserIp(request);
//		bv.setIp(ip);
//		
//		bv.setUploadedFilename(uploadedFileName);
//		// String replaceFileName = uploadedFileName.replaceAll("(\\/\\d{4}\\/\\d{2}\\/\\d{2})\\/s-", "$1/");
//        // bv.setUploadedFilename(replaceFileName);
//        
//		int bidx = 0;
//		bidx = boardService.boardInsert(bv);
//				
//		String path = "";
//		if(bidx != 0) {			
//			model.addAttribute("bidx", bidx);
//			rttr.addFlashAttribute("msg", "�۾��� ����");
//			path = "redirect:/board/" + bidx + "/boardContents.do";
//			
//		} else {
//			model.addAttribute("boardcode", boardcode);
//			model.addAttribute("period", period);
//			rttr.addFlashAttribute("msg", "�Է��� �߸��Ǿ����ϴ�.");
//			path = "redirect:/board/" + boardcode + "/" + period + "/boardWrite.do";
//		}
//		
//		return path;
//	}
//
//	@RequestMapping(value="/{bidx}/boardModify.do")
//	public String boardModify(
//			@PathVariable("bidx") int bidx,
//			Model model) {
//		
//		logger.info("boardModify����");
//				
//		BoardVo bv = boardService.boardSelectOne(bidx);		
//		model.addAttribute("bv", bv);
//		
//		String menu = "";
//		String path = "";
//		if(bv.getBoardcode().equals("travel")) {
//			if(bv.getPeriod() == 1) {
//				menu = "����ġ��";
//			} else if(bv.getPeriod() == 2) {
//				menu = "1��2��";
//			} else if(bv.getPeriod() == 3) {
//				menu = "2��3��";
//			} else if(bv.getPeriod() == 4) {
//				menu = "3��4��";
//			}
//			path = "WEB-INF/board/travelModify";
//		} else if(bv.getBoardcode().equals("free")) {
//			menu = "�����Խ���";
//			path = "WEB-INF/board/boardModify";
//		} else if(bv.getBoardcode().equals("notice")){
//			menu = "��������";
//			path = "WEB-INF/board/boardModify";
//		}
//		
//		model.addAttribute("bv", bv);
//		model.addAttribute("menu", menu);
//		
//		return path;
//		
//	}
//	
//	@RequestMapping(value="/{bidx}/boardModifyAction.do", method=RequestMethod.POST)
//	public String boardModifyAction(
//			BoardVo bv,
//			@RequestParam("attachfile") MultipartFile filename,
//			HttpServletRequest request,
//			RedirectAttributes rttr,
//			@RequestParam("isFileChange") String isFileChange
//			) throws Exception {
//		
//		
//		logger.info("boardModifyAction����");
//
//		String uploadedFileName = "";
//		if(isFileChange.equals("true")) {
//			// ����÷��(�����)
//			MultipartFile file = filename;
//			
//			if(!file.getOriginalFilename().equals("")) {
//				String uploadPath = "D:\\dev\\myprj\\myprjSpring\\myprj\\src\\main\\webapp\\resources\\boardImages\\";
//				uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
//			}
//		} else {
//			
//			BoardVo bvOrigin = boardService.boardSelectOne(bv.getBidx());
//			uploadedFileName = bvOrigin.getThumbnail();
//		}
//		
//		String ip = userip.getUserIp(request);
//		bv.setIp(ip);
//		
//		bv.setThumbnail(uploadedFileName);
//        
//		// ���� ���ε��ϰ� upadte�� �ϱ� ���� service�� �����
//		int value = boardService.boardUpdate(bv);
//		
//		String path = "";
//		if(value == 1) {
//			rttr.addFlashAttribute("msg", "�ۼ��� ����");
//			path = "redirect:/board/" + bv.getBidx() + "/boardContents.do";
//		} else {
//			rttr.addFlashAttribute("msg", "�Է��� �߸��Ǿ����ϴ�.");
//			path = "redirect:/board/" + bv.getBidx() + "/boardModify.do";
//		}
//			
//		return path;
//	}
//		
//	@RequestMapping(value="/{bidx}/boardDeleteAction.do")
//	public String boardDeleteAction(
//			@PathVariable("bidx") int bidx,
//			@RequestParam("boardcode") String boardcode,
//			@RequestParam("period") int period,
//			HttpServletRequest request,
//			RedirectAttributes rttr) {
//		
//		logger.info("boardDeleteAction����");		
//		
//		int value = boardService.boardDelete(bidx);
//
//		String path = "redirect:/board/" + boardcode + "/" + period + "/boardList.do";
//		rttr.addFlashAttribute("msg", "�ۻ��� ����");
//		if(value == 0) {
//			path = "redirect:/board/" + bidx + "/boardContent.do";
//			rttr.addFlashAttribute("msg", "�ۻ��� ����");
//		}
//		
//		return path;
//	}
//	
//	
//	
}
