package libraryGarden.user.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.mapper.LibraryBooksMapper;

/** [설명] LibraryBookService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  - 도서관 책 전체 조회 목록 출력 메서드
 *  - 도서관 책 전체 갯수 기능(페이징)
 *  - 도서관 책 상세 조회 기능
 *  
 * @author Siyeon
 */
@Service
public class LibraryBooksServiceImpl implements LibraryBooksService{

	@Autowired
	private LibraryBooksMapper lbm;
	
	// 도서관 책 전체 조회 목록 출력 메서드
	@Override
	public ArrayList<LibraryBooksDto> getBookSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());//페이지 조회 첫번째 수
		hm.put("perPageNum", scri.getPerPageNum());//페이지 조회 끝번째 수
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		
		/* 책 목록 조회
		 * [input]검색조건 외 페이지 기능 (hm)
		 * [output] 책 목록(alist)
		 */ 
		ArrayList<LibraryBooksDto> lblist =  lbm.getBookSelectAll(hm);
		return lblist;
	}

	// 도서관 책 전체 갯수(페이징)
	@Override
	public int getBookTotalCount(SearchCriteria scri) {
		
		 /* 페이징 기능 
		  * - 책 리스트 전체 갯수
		  * - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		  * [input] 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri) 
		  * [output] 조건에 따른 잭 전체 개수 cnt
		  */ 
		int cnt = lbm.getBookTotalCount(scri);
		return cnt;
	}

	// 도서관 책 상세 조회 매서드
	@Override
	public LibraryBooksDto getBookSelectOne(int lbidx) {
		/* 도서관 책 상세 조회
		 * [input] 	도서관 책 인덱스(lbidx)
		 * [output] 책 상세(lbd)
		 */ 
		LibraryBooksDto lbd = lbm.getBookSelectOne(lbidx);
		return lbd;
	}
}
