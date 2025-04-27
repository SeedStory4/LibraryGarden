package libraryGarden.user.service;

import java.util.ArrayList;
import java.util.List;

import libraryGarden.domain.LibraryBooks2Dto;
import libraryGarden.domain.LibraryBooksVo2;
import libraryGarden.domain.SearchCriteria;

public interface LibraryBook2Service {
	
	// 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBooks2Dto> BookSelectAll(SearchCriteria scri);

	// 도서관 책 전체 갯수 매서드
	int BookTotalCount(SearchCriteria scri);
	
	// 도서관 책 상세 조회 매서드
	public LibraryBooks2Dto BookSelectOne(int lbidx);
	
	List<LibraryBooks2Dto> selectTopLoanBooksThisMonth();
	 
	List<LibraryBooksVo2> getLatestBooks();
}
