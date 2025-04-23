package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libraryGarden.domain.BookVo;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.SearchCriteria;

/** [설명]AdminLibraryBooks5Service 인터페이스: 관리자 사용자 도서관 책 관련 메서드
 * 
 * [주요기능] 
 *  - 관리자 도서관 책 전체 조회 목록 출력 메서드
 *  - 관리자 도서관 책 전체 갯수 매서드(페이징)
 *  - 관리자 도서관 책 상세 조회 매서드
 *  - 관리자 도서관 책 삭제 매서드
 *  - 관리자 도서관 도서등록 시 도서관 마지막 구분 조회 매서드
 *  
 *  
 * @author SiYeon
 *
 */
public interface AdminLibraryBooksService {

	// 관리자 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBookDto> getBookSelectAll(SearchCriteria scri);

	// 관리자 도서관 책 전체 갯수 매서드
	int getBookTotalCount(SearchCriteria scri);
	
	// 관리자 도서관 책 상세 조회 매서드
	public LibraryBookDto getBookSelectOne(int lbidx);
	
	// 관리자 도서관 책 삭제 매서드
	public int getBookDeleteOne(int lbidx);
	
	// 관리자 도서관 도서등록 시 도서관 마지막 구분 조회 매서드
	public String getLibraryBookLastCode();
}
