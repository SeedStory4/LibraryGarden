package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.controller.AdminBookController;
import libraryGarden.admin.mapper.AdminBookMapper;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.mapper.Book1Mapper;

/** [설명] AdminBookService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  - 관리자 도서관 책 전체 조회 목록 출력 메서드
 *  - 관리자 도서관 책 전체 갯수 매서드(페이징)
 *  - 관리자 도서관 책 상세 조회 매서드
 *  - 관리자 도서관 책 삭제 매서드
 *  
 *  
 * @author Siyeon
 */
@Service
public class AdminBookServiceImpl implements AdminBookService{

	private static final Logger logger = LoggerFactory.getLogger(AdminBookController.class);
	
	@Autowired
	private AdminBookMapper abm;
	
	// 관리자 도서관 책 전체 조회 목록 출력 메서드
	@Override
	public ArrayList<LibraryBookDto> BookSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());//페이지 조회 첫번째 수
		hm.put("perPageNum", scri.getPerPageNum());//페이지 조회 끝번째 수
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		
		/* 책 목록 조회
		 * [input]검색조건 외 페이지 기능 (hm)
		 * [output] 책 목록(alist)
		 */ 
		ArrayList<LibraryBookDto> lblist =  abm.BookSelectAll(hm);
		return lblist;
	}

	// 관리자 도서관 책 전체 갯수 매서드(페이징)
	@Override
	public int BookTotalCount(SearchCriteria scri) {
		
		 /* 페이징 기능 
		  * - 책 리스트 전체 갯수
		  * - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		  * [input] 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri) 
		  * [output] 조건에 따른 잭 전체 개수 cnt
		  */ 
		int cnt = abm.BookTotalCount(scri);
		return cnt;
	}

	// 관리자 도서관 책 상세 조회 매서드
	@Override
	public LibraryBookDto BookSelectOne(int lbidx) {
		/* 도서관 책 상세 조회
		 * [input] 	도서관 책 인덱스(lbidx)
		 * [output] 책 상세(lbd)
		 */ 
		LibraryBookDto lbd = abm.BookSelectOne(lbidx);
		return lbd;
	}

	// 관리자 도서관 책 삭제 매서드
	@Override
	public int BookDeleteOne(int lbidx) {
		/* 도서관 책 상세 조회
		 * [input] 	도서관 책 인덱스(lbidx)
		 * [output] 삭제 여부 값(value)
		 */ 
		int value = abm.BookDeleteOne(lbidx);
		logger.debug("AdminBookServiceImpl BookDeleteOne value" + value);
		return value;
	}
	
}
