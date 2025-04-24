package libraryGarden.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import libraryGarden.domain.LibraryBook2Dto;
import libraryGarden.domain.LibraryBooksVo2;
import libraryGarden.domain.SearchCriteria;

public interface LibraryBook2Mapper {
	
	// 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBook2Dto> BookSelectAll(HashMap<String,Object> hm);

	// 도서관 책 전체 갯수 매서드
	int BookTotalCount(SearchCriteria scri);
	
	// 도서관 책 상세 조회 매서드
	public LibraryBook2Dto BookSelectOne(int lbidx);
	
	List<LibraryBook2Dto> selectTopLoanBooksThisMonth();
	
	List<LibraryBooksVo2> getLatestBooks();
}
