package libraryGarden.admin.controller;

import java.util.ArrayList;
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
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.PageMaker;
import libraryGarden.domain.SearchCriteria;

@Controller
@RequestMapping("/admin/book")
public class AdminBookController2 {

	private static final Logger logger = LoggerFactory.getLogger(AdminBookController2.class);

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
					
		 logger.debug("bookList 들어옴");

		// 현재 페이지 저장
	     SearchCriteria scri = new SearchCriteria();
	     scri.setPage(page);
		 pm.setScri(scri);

	     // API를 위해 사용자가 입력한 검색조건과 검색어 및 현재 페이지 세팅
	     int start = page;
	     String searchWord = keyword;
	     String queryType = searchType;
	     
	 	 HashMap<String, Object> hm = new HashMap<String, Object>();
	 	 List<BooksVo> alist = new ArrayList<>();  // List는 인터페이스이기 때문에 객체 생성을 못함 -> List<>()가 아닌 ArrayList<>()로 초기화
	 	int totalCount = 0;
	 	
	     try {
	    	ApiBookPageDto abpd = aladdinOpenAPI.searchBooksList(searchWord, queryType, start);

	        if (abpd != null && !abpd.getBlist().isEmpty()) {

	            alist = abpd.getBlist();
	            totalCount = abpd.getTotalCount();
	            
	        }

	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	     
         // 페이징을 위한 전체 데이터 갯수 저장
		 pm.setTotalCount(totalCount);
			
		 hm.put("alist", alist);  // 책 리스트
		 hm.put("pm", pm);  // 페이징 정보
		 
		 return hm;
		
	}
   
}