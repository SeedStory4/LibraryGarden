package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import libraryGarden.admin.mapper.AdminLibraryBooksMapper;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.LibraryBooksVo;
import libraryGarden.domain.SearchCriteria;


/** [설명] AdminBookService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  - 관리자 도서관 책 전체 조회 목록 출력 메서드
 *  - 관리자 도서관 책 전체 갯수 매서드(페이징)
 *  - 관리자 도서관 책 상세 조회 매서드
 *  - 관리자 도서관 책 삭제 매서드
 *  - 관리자 도서관 도서등록 시 도서관 마지막 구분 조회 매서드
 *  
 * @author Siyeon
 */
@Service
public class AdminLibraryBooksServiceImpl implements AdminLibraryBooksService{

	private static final Logger logger = LoggerFactory.getLogger(AdminLibraryBooksServiceImpl.class);
	
	@Autowired
	private AdminLibraryBooksMapper albm;
	
	
	// 관리자 도서관 책 전체 조회 목록 출력 메서드
	@Override
	public ArrayList<LibraryBookDto> getBookSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());//페이지 조회 첫번째 수
		hm.put("perPageNum", scri.getPerPageNum());//페이지 조회 끝번째 수
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		
		/* 책 목록 조회
		 * [input]검색조건 외 페이지 기능 (hm)
		 * [output] 책 목록(alist)
		 */ 
		ArrayList<LibraryBookDto> lblist =  albm.getBookSelectAll(hm);
		return lblist;
	}

	// 관리자 도서관 책 전체 갯수 매서드(페이징)
	@Override
	public int getBookTotalCount(SearchCriteria scri) {
		
		 /* 페이징 기능 
		  * - 책 리스트 전체 갯수
		  * - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		  * [input] 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri) 
		  * [output] 조건에 따른 잭 전체 개수 cnt
		  */ 
		int cnt = albm.getBookTotalCount(scri);
		return cnt;
	}

	// 관리자 도서관 책 상세 조회 매서드
	@Override
	public LibraryBookDto getBookSelectOne(int lbidx) {
		/* 도서관 책 상세 조회
		 * [input] 	도서관 책 인덱스(lbidx)
		 * [output] 책 상세(lbd)
		 */ 
		LibraryBookDto lbd = albm.getBookSelectOne(lbidx);
		return lbd;
	}

	// 관리자 도서관 책 삭제 매서드
	@Override
	public int getBookDeleteOne(int lbidx) {
		/* 도서관 책 상세 조회
		 * [input] 	도서관 책 인덱스(lbidx)
		 * [output] 삭제 여부 값(value)
		 */ 
		int value = albm.getBookDeleteOne(lbidx);
		logger.debug("AdminBookServiceImpl BookDeleteOne value" + value);
		return value;
	}

	// 관리자 도서관 도서등록 시 도서관 마지막 구분 조회 매서드
	@Override
	public String getLibraryBookLastCode() {
		String lastCode = albm.getLibraryBookLastCode();
		return lastCode;
	}
	
	// 관리자 도서관 청구기호 일치여부 숫자 조회 매서드
	@Override
	public int getCheckCallNumberDuplicate(String callName) {
		int cnt = albm.getCheckCallNumberDuplicate(callName);
		return cnt;
	}

	@Override
	public int insertLibraryBookAboutBook(LibraryBooksVo lbv) {
		int cnt = albm.insertLibraryBookAboutBook(lbv);
		return cnt;
	}


	
}
