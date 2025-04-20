package libraryGarden.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import libraryGarden.domain.LibraryBookDto2;
import libraryGarden.domain.LibraryBooksVo2;
import libraryGarden.domain.SearchCriteria;

public interface LibraryBookMapper2 {
	
	// 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBookDto2> BookSelectAll(HashMap<String,Object> hm);

	// 도서관 책 전체 갯수 매서드
	int BookTotalCount(SearchCriteria scri);
	
	// 도서관 책 상세 조회 매서드
	public LibraryBookDto2 BookSelectOne(int lbidx);
	
	List<LibraryBookDto2> selectTopLoanBooksThisMonth();
	
	List<LibraryBooksVo2> getLatestBooks();
}
