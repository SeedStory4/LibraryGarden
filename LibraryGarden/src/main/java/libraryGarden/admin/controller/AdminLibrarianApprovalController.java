package libraryGarden.admin.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.admin.service.AdminLibrarianApprovalService;

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
		
		 pm.setScri(scri);
		 String filter = ad.getStatus();
		 
		 int cnt = librarianApprovalService.librarianApprovalTotalCount(scri, filter);
		 pm.setTotalCount(cnt);
		
		 ArrayList<ApprovalDto> alist = librarianApprovalService.librarianApprovalSelectAll(scri, filter);
		 
		 model.addAttribute("alist", alist);
		 model.addAttribute("pm", pm);
		
		return "admin/librarianApproval/librarianApprovalList";
	}
	
//	@RequestMapping(value="/{boardcode}/{period}/boardList.do")
//	public String boardList(
//			@PathVariable("boardcode") String boardcode,
//			@PathVariable("period") int period,
//			SearchCriteria scri,
//			Model model) {
//		
//		logger.info("boardList����");
//		
//		pm.setScri(scri);  // <-- PageMaker�� SearhCriteria ��Ƽ� ������ �ٴѴ�
//		
//		// ����¡ ó���ϱ� ���� ��ü ������ ���� ��������
//		int cnt = boardService.boardTotalCount(scri, boardcode, period);
//		
//		pm.setTotalCount(cnt);  // <-- PageMaker�� ��ü�Խù����� ��Ƽ� ������ ���
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
//			path = "WEB-INF/board/travelList";
//		} else if(boardcode.equals("free")) {
//			menu = "�����Խ���";
//			path = "WEB-INF/board/boardList";
//		} else if(boardcode.equals("notice")){
//			menu = "��������";
//			path = "WEB-INF/board/boardList";
//		}
//		
//		ArrayList<BoardDto> blist = boardService.boardSelectAll(scri, boardcode, period);
//		model.addAttribute("blist", blist);	 // ȭ����� ������ �������� model ��ü�� ��´�(redirect ��� ���ϹǷ� Modele�� ���)
//		model.addAttribute("pm", pm);  // forward ������� �ѱ�� ������ ������ �����ϴ�
//		model.addAttribute("menu", menu);
//		model.addAttribute("boardcode", boardcode);
//		model.addAttribute("period", period);
//		
//		return path;
//	}
//	
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
//	@RequestMapping(value="/imagePreview.do", method=RequestMethod.POST)
//	public ResponseEntity<Map<String, String>> imagePreview(@RequestParam("upload") MultipartFile upload, HttpServletRequest request) {
//		
//		System.out.println("imagePreview ����");
//		
//		// ���� ���� ���丮. �������
//		String uploadDirectory = "D:\\dev\\myprj\\myprjSpring\\myprj\\src\\main\\webapp\\resources\\ckeditor5Builder\\ckeditor5\\imagePreview\\";
//		File directory = new File(uploadDirectory);
//		
//		// ���丮�� �������� ������ ����
//		if (!directory.exists()) {
//			directory.mkdirs();  // ���丮 ����
//		}
//		
//	    String fileName = UUID.randomUUID().toString() + "_" + upload.getOriginalFilename();  // ������ ���� �̸� ����
//	    File file  = new File(directory, fileName);
//
//	    try {
//	        // ������ ������ ����
//	        upload.transferTo(file);
//	        
//	        // ���ε�� ������ URL�� ��ȯ
//	        String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + 
//	                "/board/displayFile.do?fileName=" + fileName + "&type=" + "preview";
//	        
//	        // Map<String, Object> response = new HashMap<>();
//	        // response.put("uploaded", true); // ���ε� ���� ����
//	        // response.put("url", fileUrl);
//
//	        Map<String, String> response = Map.of("url", fileUrl);
//	        return ResponseEntity.ok(response); // JSON �������� ��ȯ
//	        
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	    }
//	}
//	
//	@RequestMapping(value="/displayFile.do", method=RequestMethod.GET)  // �����ο� ������ �Ǿ�� ��
//	public ResponseEntity<byte[]> displayFile(
//			@RequestParam("fileName") String fileName,
//			@RequestParam("type") String type  // �̸����� �̹������� ����
//			) {
//		
//		logger.info("displayFile����");
//	
//		ResponseEntity<byte[]> entity = null;  // ResponseEntity : Collectionó�� ��ü�� ��´�.
//		InputStream in = null;
//		
//		try{
//			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);  // ������ Ȯ���ڸ� ����
//			MediaType mType = MediaUtils.getMediaType(formatName);  // MediaUtils�� Ȯ���ڸ� �־ ������ Ÿ���� �˾Ƴ�
//			
//			HttpHeaders headers = new HttpHeaders();		
//			System.out.println("type : " + type);
//			String uploadPath = "";
//			if(type.equals("preview")) {
//				uploadPath = "D:\\dev\\myprj\\myprjSpring\\myprj\\src\\main\\webapp\\resources\\ckeditor5Builder\\ckeditor5\\imagePreview\\";		
//			} else if(type.equals("thumbnail")) {
//				uploadPath = "D:\\dev\\myprj\\myprjSpring\\myprj\\src\\main\\webapp\\resources\\boardImages\\";
//			}
//			
//			in = new FileInputStream(uploadPath+fileName);  // ���� �б�
//		
//			if(mType != null){  // ������ Ÿ���� JPG, GIF, PNG �� �ϳ��� ���				
//				headers.setContentType(mType);
//			}
//			
//			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);  // �������� �Ű������� ���� �޾Ƽ� ����
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
//			
//		}finally{
//			try {
//				in.close();
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}
//		}
//				
//		return entity;
//	}
//	
//	@RequestMapping(value="/{bidx}/boardContents.do")
//	public String boardContents(@PathVariable("bidx") int bidx, Model model) {
//
//		logger.info("boardContents ����");
//		
//		boardService.boardViewCntUpdate(bidx);  // ��ȸ�� ������Ʈ �ϱ�
//		BoardVo bv = boardService.boardSelectOne(bidx);  // �ش�Ǵ� bidx�� �Խù� ������ ������
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
//			path = "WEB-INF/board/travelContents";
//		} else if(bv.getBoardcode().equals("free")) {
//			menu = "�����Խ���";
//			path = "WEB-INF/board/boardContents";
//		} else if(bv.getBoardcode().equals("notice")){
//			menu = "��������";
//			path = "WEB-INF/board/boardContents";
//		}
//		
//		model.addAttribute("bv", bv);
//		model.addAttribute("menu", menu);
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
////		// ������ ������ ���� ��ο� ���� �ű��
////		@RequestMapping(value="/displayFile.aws", method=RequestMethod.GET)  // �����ο� ������ �Ǿ�� ��
////		public ResponseEntity<byte[]> displayFile(
////				@RequestParam("fileName") String fileName,
////				@RequestParam(value="down", defaultValue="0") int down  // �ٿ� ������, ȭ�鿡�� �������� ����
////				) {
////
////			logger.info("displayFile����");
////			
////			ResponseEntity<byte[]> entity = null;  // ResponseEntity : Collectionó�� ��ü�� ��´�.
////			InputStream in = null;
////			
////			try{
////				String formatName = fileName.substring(fileName.lastIndexOf(".")+1);  // ������ Ȯ���ڸ� ����
////				MediaType mType = MediaUtils.getMediaType(formatName);  // MediaUtils�� Ȯ���ڸ� �־ ������ Ÿ���� �˾Ƴ�
////				
////				HttpHeaders headers = new HttpHeaders();		
////				 
////				in = new FileInputStream(uploadPath+fileName);  // ���� �б�
////							
////				if(mType != null){  // ������ Ÿ���� JPG, GIF, PNG �� �ϳ��� ���
////					
////					if (down==1) {  // �ٿ��� �޴´�
////						fileName = fileName.substring(fileName.indexOf("_")+1);
////						headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
////						headers.add("Content-Disposition", "attachment; filename=\""+
////								new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
////						
////					}else {  // �ٿ���� �ʰ� Ÿ���� ����
////						headers.setContentType(mType);	
////					}
////					
////				}else{  // �̸����� ���� �ʰ� �ٿ��� �޴´�.
////					
////					fileName = fileName.substring(fileName.indexOf("_")+1);
////					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
////					headers.add("Content-Disposition", "attachment; filename=\""+
////							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
////				}
////				
////				entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);  // �������� �Ű������� ���� �޾Ƽ� ����
////				
////			}catch(Exception e){
////				e.printStackTrace();
////				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
////				
////			}finally{
////				try {
////					in.close();
////				} catch (IOException e) {
////					
////					e.printStackTrace();
////				}
////			}
////					
////			return entity;
////		}
//	
//		
//	@RequestMapping(value="boardReply.aws", method=RequestMethod.GET)
//	public String boardReply(
//		@RequestParam("bidx") int bidx,
//		Model model) {
//
//		logger.info("boardReply����");
//		
//		BoardVo bv = boardService.boardSelectOne(bidx);
//		
//		model.addAttribute("bv", bv);
//		
//		return "WEB-INF/board/boardReply";
//	
//	}
//
//	@RequestMapping(value="boardReplyAction.aws", method=RequestMethod.POST)
//	public String boardReplyAction(
//			BoardVo bv,
//			@RequestParam("attachfile") MultipartFile filename,  // input�� name �̸��� BoardVo�� �ִ� ������Ƽ �̸��� �����ϸ� BoardVo�� ���� �Ѿ�� @RequestParam���� ���� �� �����Ƿ�, input�� name�� filename�� �ƴ� attachfile���� �Ѵ�.
//			HttpServletRequest request,
//			RedirectAttributes rttr
//			) throws Exception {
//		
//		logger.info("boardReplyAction����");
//		
//		// ����÷��
//		MultipartFile file = filename;
//		String uploadedFileName = "";
//		
//		if(!file.getOriginalFilename().equals("")) {			
//			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
//		}
//
//		String midx = request.getSession().getAttribute("midx").toString();
//		int midx_int = Integer.parseInt(midx);
//		String ip = userip.getUserIp(request);
//
//		bv.setUploadedFilename(uploadedFileName);
//		bv.setMidx(midx_int);
//		bv.setIp(ip);		
//		
//		int maxBidx = 0;
//		maxBidx = boardService.boardReply(bv);
//
//		String path = "";
//		if (maxBidx != 0) {
//			rttr.addFlashAttribute("msg", "�亯 ��� ����");
//			path = "redirect:/board/boardContents.aws?bidx=" + maxBidx;
//			
//		} else {
//			rttr.addFlashAttribute("msg", "�亯�� ��ϵ��� �ʾҽ��ϴ�.");
//			path ="redirect:/board/boardReply.aws?bidx=" + bv.getBidx();
//			
//		}
//		
//		return path;
//	}
}
