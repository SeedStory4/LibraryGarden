package libraryGarden.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.admin.service.AdminBookRequestService;
import libraryGarden.cmm.util.UrlEncoder;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;


@Controller
@RequestMapping("/admin/bookRequest")
public class AdminBookRequestController {
	


/*
 * [추가] 관리자 희망 도서 목록 페이지 이동(팝업)
 * 
 *  
 * @author JiHye
 * @write 2024.04.03
 * 
 */

	private static final Logger logger = LoggerFactory.getLogger(AdminBookRequestController.class);

	@Autowired(required=false)
	private AdminBookRequestService bookRequestService;
	
	@Autowired(required=false)
	private PageMaker pm;
	
	// 희망 도서 목록 페이지 이동(팝업)
	@ResponseBody
	@RequestMapping(value="/bookRequestList.do", method = RequestMethod.POST)
	public HashMap<String, Object> bookRequestList(
			@RequestParam(value = "searchType", defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page
		 ) {
					
		 logger.info("bookRequestList 들어옴");
		 
		 // 사용자가 입력한 검색조건과 검색어 저장
		 SearchCriteria scri = new SearchCriteria();
		 scri.setSearchType(searchType);
		 scri.setKeyword(keyword);
		 scri.setPage(page);
		 pm.setScri(scri);
		 
		 // 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		 int cnt = bookRequestService.bookRequestTotalCount(scri);
		 pm.setTotalCount(cnt);
		 
		 System.out.println("cnt : " + cnt);
		
		 // 목록에서 보여줄 데이터 DB에서 가져오기
		 ArrayList<RequestDto> alist = bookRequestService.bookRequestSelectAll(scri);
		 
		 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
		 UrlEncoder encoder = new UrlEncoder();
		 scri.setKeyword(encoder.encoding(scri.getKeyword()));
		 
		 HashMap<String, Object> hm = new HashMap<String, Object>();
		 hm.put("alist", alist);
		 hm.put("pm", pm);
		 
		 return hm;
		
	}
   
}