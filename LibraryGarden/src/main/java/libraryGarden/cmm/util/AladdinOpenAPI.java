package libraryGarden.cmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import libraryGarden.domain.ApiBookPageDto;
import libraryGarden.domain.BooksVo;


import java.util.List;
import java.util.ArrayList;
import java.util.Map;


import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * [설명] 알라딘 도서 API
 * 
 * [주요기능]
 * - 알라딘 상품 검색 API - 리스트 출력
 * - 알라딘 상품 조회 API - 개당 출력
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */

@Component
@PropertySource("classpath:/egovframework/egovProps/application-API-KEY.properties")
public class AladdinOpenAPI {

	private static final Logger logger = LoggerFactory.getLogger(AladdinOpenAPI.class);

	// 알라딘 api 키 주입
    @Value("${ALADIN-KEY}")
    private String aladinKey;    
    
	// 알라딘 상품 검색 API 링크 - 목록 출력
	private static final String BOOKS_SEARCH_LIST = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?";

	// 알라딘 상품 조회 API 링크 - 도서의 한개에 대해 자세하게 줄력 출력
	private static final String BOOK_LOOKUP_DETAIL = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?";
	
	// 알라딘 상품 검색 API 메서드
	public ApiBookPageDto searchBooksList(String searchWord, String queryType , int start) throws Exception {
		
		String query = searchWord;
		String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
		
		//logger.debug("🔍 AladdinOpenAPI searchBooksList 들어옴");
		// URL 파라미터 구성
		Map<String,Object> hm = new HashMap<String,Object>();
		hm.put("ttbkey", aladinKey);
		hm.put("Query", encodedQuery);
		hm.put("QueryType", queryType);
		hm.put("MaxResults", "12");
		hm.put("start", start);
		hm.put("SearchTarget", "Book");
		hm.put("output", "js");
		hm.put("Version", "20131101");

		// URL 조립
		StringBuffer sb = new StringBuffer(); 
		Iterator<String> iter = hm.keySet().iterator(); 

		while (iter.hasNext()) { 
			String key = iter.next();  
			String val  = hm.get(key).toString();; 
			sb.append(key).append("=").append(val).append("&"); 
		}

		String booksSearchListUrl = BOOKS_SEARCH_LIST + sb.toString();
		logger.debug("AladdinOpenAPI searchBooksList booksSearchListUrl : " + booksSearchListUrl);
		  
		// HTTP 요청
	    URL url = new URL(booksSearchListUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");

	    int statusCode = conn.getResponseCode();
	    logger.debug("AladdinOpenAPI searchBooksList HTTP 응답 코드: " + statusCode);
	    
	    BufferedReader rd = (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
	        ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
	        : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

	    StringBuilder response = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	        response.append(line);
	    }
	    rd.close();
	    conn.disconnect();
	    logger.debug("AladdinOpenAPI searchBooksList Raw JSON 응답: " + response.toString());
	    
	    // JSON 파싱
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(response.toString());
	    int totalCount = root.path("totalResults").asInt(); 
	    
	    JsonNode itemsNode = root.path("item");
	    List<BooksVo> blist = new ArrayList<>();

	    for (JsonNode node : itemsNode) {
	    	BooksVo bv = new BooksVo();

	    	// 해당 api에 없는 데이터  = 원제, 부제, 쪽수, 넓이 , 높이, 무게, 
	    	
	    	// 책 표지
	    	bv.setCoverImg(node.path("cover").asText());

	        // 책 제목
	    	bv.setTitle(node.path("title").asText());

	        // 책 저자
	    	bv.setAuthor(node.path("author").asText());

	    	// 책 출판사
	    	bv.setPublisher(node.path("publisher").asText());
	    	
	    	// 책 출판일
	    	bv.setPublishedYear(node.path("pubDate").asText());
	    	
	    	// 책 isbn
	    	bv.setIsbn(node.path("isbn13").asText());
	    	
	    	// 책 표지
	    	bv.setCategory(node.path("categoryName").asText());
	    	
	    	// 책 소개(한 줄 요약)
	    	bv.setIntroduction(node.path("description").asText());
	    	
	    	// 책 정가
	    	bv.setPrice(Integer.valueOf(node.path("priceStandard").asText()));
	    	
	    	logger.debug("AladdinOpenAPI searchBooksList bv 확인 "+" 표지/coverImg: "+bv.getCoverImg()
	    	+" \n체목/title: "+bv.getTitle()+" 저자/author: "+bv.getAuthor()
	    	+" \n출판사/publisher: "+bv.getPublisher()+" 출판일/publishedYear: "+bv.getPublishedYear()+" isbn/isbn: "+bv.getIsbn()
	    	+" \n카테고리/category: "+bv.getCategory()+" 한줄요약/introduction: "+bv.getIntroduction()+" 정가/price: "+bv.getPrice());
	        
	    	blist.add(bv);
	    }

	    ApiBookPageDto abpd = new ApiBookPageDto();
	    abpd.setBlist(blist);
	    abpd.setTotalCount(totalCount);// 페이징을 위한 담기 위한 전체 갯수
      
        //logger.debug("AladdinOpenAPI searchBooksList totalCount " + totalCount);
	    return abpd;
	}
	
	// 알라딘 상품 조회 API 메서드
	public BooksVo lookUpBookDetail(String isbn) throws Exception {
		logger.debug("🔍 AladdinOpenAPI searchBooksList 들어옴");
		Map<String,Object> hm = new HashMap<String,Object>();
		hm.put("ttbkey", aladinKey);
		hm.put("ItemIdType", "ISBN13");
		hm.put("ItemId", isbn );
		hm.put("output", "js");
		hm.put("OptResult", "packing");
		hm.put("Version", "20131101");
		
		StringBuffer sb = new StringBuffer(); 
		Iterator<String> iter = hm.keySet().iterator(); 

		while (iter.hasNext()) { 
			String key = iter.next();  
			String val  = hm.get(key).toString(); 
			sb.append(key).append("=").append(val).append("&"); 
		}
		
		String bookLookUpDetailUrl = BOOK_LOOKUP_DETAIL + sb.toString();
		// logger.debug("AladdinOpenAPI lookUpBookDetail bookLookUpDetailUrl : " + bookLookUpDetailUrl);

		// HTTP 요청
	    URL url = new URL(bookLookUpDetailUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");

	    int statusCode = conn.getResponseCode();
	    logger.debug("AladdinOpenAPI lookUpBookDetail HTTP 응답 코드: " + statusCode);
	    
	    BufferedReader rd = (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
	        ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
	        : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

	    StringBuilder response = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	        response.append(line);
	    }
	    rd.close();
	    conn.disconnect();
		//logger.debug("AladdinOpenAPI lookUpBookDetail Raw JSON 응답: " + response.toString());
		    
	    // JSON 파싱
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode root = mapper.readTree(response.toString());
	    JsonNode item = root.path("item").get(0);
	    JsonNode itemSubInfo = item.path("subInfo");
	    JsonNode itemSubInfoPacking = itemSubInfo.path("packing");

    	BooksVo bv = new BooksVo();

    	
    	// 책 표지
    	bv.setCoverImg(item.path("cover").asText());
    	
        // 책 제목
    	bv.setTitle(item.path("title").asText());
    	
    	// 책 원제 - 외국책 원래 이름
    	bv.setOriginalTitle(itemSubInfo.path("originalTitle").asText());
    	
    	// 책 부제
    	bv.setSubTitle(itemSubInfo.path("subTitle").asText());
    	
        // 책 저자
    	bv.setAuthor(item.path("author").asText());

    	// 책 출판사
    	bv.setPublisher(item.path("publisher").asText());
    	
    	// 책 출판일
    	bv.setPublishedYear(item.path("pubDate").asText());
    	
    	// 책 쪽수
    	bv.setTotalPages(Integer.valueOf(itemSubInfo.path("itemPage").asText()));
     
    	// 책 isbn
    	bv.setIsbn(item.path("isbn13").asText());
    	
    	// 책 높이
    	bv.setSizeWidth(itemSubInfoPacking.path("sizeWidth").asText());
    	
    	// 책 넓이
    	bv.setSizeHeight(itemSubInfoPacking.path("sizeHeight").asText());
     	
    	// 책 무게
    	bv.setWeight(itemSubInfoPacking.path("weight").asText());
    	
    	// 책 표지
    	bv.setCategory(item.path("categoryName").asText());
    	
    	// 책 소개(한 줄 요약)
    	bv.setIntroduction(item.path("description").asText());
    	
    	// 책 정가
    	bv.setPrice(Integer.valueOf(item.path("priceStandard").asText()));

    	//logger.debug("AladdinOpenAPI searchBooksList bv 확인 "+" 표지/coverImg: "+bv.getCoverImg()
    	//	 +" \n체목/title: "+bv.getTitle()+" 저자/author: "+bv.getAuthor()
    	//	 +" \n출판사/publisher: "+bv.getPublisher()+" 출판일/publishedYear: "+bv.getPublishedYear()+" isbn/isbn: "+bv.getIsbn()
    	//	 +" \n카테고리/category: "+bv.getCategory()+" 한줄요약/introduction: "+bv.getIntroduction()+" 정가/price: "+bv.getPrice());

    	//logger.debug("AladdinOpenAPI searchBooksList bv 확인2 "+" 원제/originalTitle: "+bv.getOriginalTitle()
    	//   +" \n부제/subTitle: "+bv.getSubTitle()+" 쪽수/totalPages: "+bv.getTotalPages()
    	//   +" \n넓이/sizeWidth: "+bv.getSizeWidth()+" 높이/sizeHeight: "+bv.getSizeHeight()+" 무게/weight: "+bv.getWeight());

        return bv;

	}
	
}
