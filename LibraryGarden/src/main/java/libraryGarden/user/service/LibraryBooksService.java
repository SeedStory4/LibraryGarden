package libraryGarden.user.service;

import java.util.ArrayList;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.SearchCriteria;

/** [설명]LibraryBookService 인터페이스: 사용자 도서관 책 관련 메서드
 * 
 * [주요기능] 
 * - 도서관 책 전체 조회 목록 출력 메서드
 * - 도서관 책 전체 갯수 매서드
 * - 도서관 책 상세 조회 매서드
 * 
 * @author SiYeon
 */
public interface LibraryBooksService {
	
	// 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBooksDto> getBookSelectAll(SearchCriteria scri);

	// 도서관 책 전체 갯수 매서드
	int getBookTotalCount(SearchCriteria scri);
	
	// 도서관 책 상세 조회 매서드
	public LibraryBooksDto getBookSelectOne(int lbidx);
}
