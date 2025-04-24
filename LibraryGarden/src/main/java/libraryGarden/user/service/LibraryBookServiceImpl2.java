package libraryGarden.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.LibraryBookDto2;
import libraryGarden.domain.LibraryBooksVo2;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.mapper.LibraryBooksMapper;
import libraryGarden.user.mapper.LibraryBookMapper2;

@Service
public class LibraryBookServiceImpl2 implements LibraryBookService2{

	@Autowired
	private LibraryBookMapper2 lbm;
	
	// 도서관 책 전체 조회 목록 출력 메서드
	@Override
	public ArrayList<LibraryBookDto2> BookSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());//페이지 조회 첫번째 수
		hm.put("perPageNum", scri.getPerPageNum());//페이지 조회 끝번째 수
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		
		/* 책 목록 조회
		 * [input]검색조건 외 페이지 기능 (hm)
		 * [output] 책 목록(alist)
		 */ 
		ArrayList<LibraryBookDto2> lblist =  lbm.BookSelectAll(hm);
		return lblist;
	}

	// 도서관 책 전체 갯수(페이징)
	@Override
	public int BookTotalCount(SearchCriteria scri) {
		
		 /* 페이징 기능 
		  * - 책 리스트 전체 갯수
		  * - 페이징을 위한 전체 데이터 갯수 DB에서 가져오기
		  * [input] 검색조건 searchType / 검색어 keyword 외 페이지 기능(scri) 
		  * [output] 조건에 따른 잭 전체 개수 cnt
		  */ 
		int cnt = lbm.BookTotalCount(scri);
		return cnt;
	}

	// 도서관 책 상세 조회 매서드
	@Override
	public LibraryBookDto2 BookSelectOne(int lbidx) {
		/* 도서관 책 상세 조회
		 * [input] 	도서관 책 인덱스(lbidx)
		 * [output] 책 상세(lbd)
		 */ 
		LibraryBookDto2 lbd = lbm.BookSelectOne(lbidx);
		return lbd;
	}

	@Override
	public List<LibraryBookDto2> selectTopLoanBooksThisMonth() {
		return lbm.selectTopLoanBooksThisMonth();
	}

	@Override
	public List<LibraryBooksVo2> getLatestBooks() {
		return lbm.getLatestBooks();  // Mapper에 해당 메서드 추가 필요
	}
}
