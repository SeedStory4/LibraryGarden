package libraryGarden.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.LibraryBooksVo;
import libraryGarden.domain.SearchCriteria;


public interface LibraryBooksMapper {
	
	/** [설명]LibraryBookMapper 인터페이스: 사용자 도서관 책 관련 메서드
	 * 
	 * [주요기능] 
	 * - 도서관 책 전체 조회 목록 출력 메서드
	 * - 도서관 책 전체 갯수 매서드
	 * - 도서관 책 상세 조회 매서드
	 * 
	 * @author SiYeon
	 */
	
	// 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBooksDto> getBookSelectAll(HashMap<String,Object> hm);

	// 도서관 책 전체 갯수 매서드
	int getBookTotalCount(SearchCriteria scri);
	
	// 도서관 책 상세 조회 매서드
	public LibraryBooksDto getBookSelectOne(int lbidx);

	/** 
	 * @author chan
	 */
	
	List<LibraryBooksDto> selectTopLoanBooksThisMonth();
	
	List<LibraryBooksDto> selectLatestLibraryBooks();
}
