package libraryGarden.admin.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import libraryGarden.cmm.util.AladdinOpenAPI;
import libraryGarden.domain.ApiBookPageDto;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;

@Controller
@RequestMapping("/admin/book")
public class AdminBookController2 {

	private static final Logger logger = LoggerFactory.getLogger(AdminBookController2.class);

	// AladdinOpenAPI 주입
	@Autowired(required = false)
	AladdinOpenAPI aladdinOpenAPI;
	
	@Autowired(required=false)
	private PageMaker pm;
	
	// 도서 목록 페이지 이동(팝업)
	@ResponseBody
	@RequestMapping(value="/bookList.do", method = RequestMethod.POST)
	public HashMap<String, Object> bookList(
			@RequestParam(value = "searchType", defaultValue = "title") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page
		 ) {
					
		 logger.info("bookList 들어옴");
		 

		 


		    // 페이징 처리 준비
		    int start = page; // API 요청 시작 인덱스
		    String searchWord = keyword;
		    String queryType = searchType;


			HashMap<String, Object> hm = new HashMap<String, Object>();

//			// 검색어 필수 검증
//		    if(searchWord == null){
//		    	return "admin/book/bookList";
//		    }
//		    
//		    if (searchWord.trim().isEmpty()) {
//		    	hm.put("msg", "검색어를 입력해주세요.");
//		        return "user/bookRequest/bookRequestWrite";
//		    }

		    ApiBookPageDto abpd = null;
		    try {
		    	abpd = aladdinOpenAPI.searchBooksList(searchWord, queryType, start);

		        if (abpd != null && !abpd.getBlist().isEmpty()) {
		            
		            List<BookVo> blist = abpd.getBlist();
		            int totalCount = abpd.getTotalCount();

			   		// 사용자가 입력한 검색조건과 검색어 저장
			   		SearchCriteria scri = new SearchCriteria();
			   		scri.setSearchType(searchType);
			   		scri.setKeyword(keyword);
			   		scri.setPage(page);
			   		pm.setScri(scri);
					pm.setTotalCount(totalCount);
					
//					 // URL에서 특수문자가 포함된 검색어를 사용할 때 오류가 발생하지 않도록 인코딩 처리
//					 UrlEncoder encoder = new UrlEncoder();
//					 scri.setKeyword(encoder.encoding(scri.getKeyword()));
//					 
					 hm.put("blist", blist);  // 책 리스트
					 hm.put("pm", pm);  // 페이징 정보
					 
		        }

		    } catch (Exception e) {
		        e.printStackTrace(); // 개발자용 로그
		    }

			 return hm;
		
	}
   
}