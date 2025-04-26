package libraryGarden.admin.service;

import java.util.ArrayList;

import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.LibraryBooksVo;
import libraryGarden.domain.SearchCriteria;

/** [설명]AdminLibraryBooks5Service 인터페이스: 관리자 사용자 도서관 책 관련 메서드
 * 
 * [주요기능] 
 *  - 관리자 도서관 책 전체 조회 목록 출력 메서드
 *  - 관리자 도서관 책 전체 갯수 매서드(페이징)
 *  - 관리자 도서관 책 상세 조회 매서드
 *  - 관리자 도서관 책 삭제 매서드
 *  - 관리자 도서관 도서등록 시 도서관 마지막 구분 조회 매서드
 *  - 관리자 도서관 청구기호 일치여부 숫자 조회 매서드
 *  - 관리자 도서관 도서 등록 매서드
 *  - 관리자 도서관 오늘자 도서 등록 조회 목록 출력 메서드
 *  - 관리자 도서관 오늘자 도서 등록 갯수 조회 매서드
 *  - 관리자 도서관 aidx를 가지고 오는 매서드
 *  - 관리자 도서관 lbidx, bidx, aidx를 가지고 오는 매서드
 *  - 관리자 도서관 도서 수정 매서드
 *  
 * @author SiYeon
 */
public interface AdminLibraryBooksService {

	// 관리자 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBookDto> getBookSelectAll(SearchCriteria scri);

	// 관리자 도서관 책 전체 갯수 매서드
	int getBookTotalCount(SearchCriteria scri);
	
	// 관리자 도서관 책 상세 조회 매서드
	public LibraryBookDto getBookSelectOne(int lbidx);
	
	// 관리자 도서관 책 삭제 매서드
	public int getLibraryBookDeleteOne(int lbidx);
	
	// 관리자 도서관 도서등록 시 도서관 마지막 구분 조회 매서드
	public String getLibraryBookLastCode();
	
	// 관리자 도서관 청구기호 일치여부 숫자 조회 매서드
	public int getCheckCallNumberDuplicate(String callName);
	
	// 관리자 도서관 도서 등록 매서드
	public int insertLibraryBookAboutBook(LibraryBooksVo lbv);

    // 관리자 도서관 오늘자 도서 등록 조회 목록 출력 메서드
	public ArrayList<LibraryBookDto> getBookWriteListSelectAll(SearchCriteria scri, String today);
	
	// 관리자 도서관 오늘자 도서 등록 갯수 조회 매서드
	public int getBookWriteListCount(String today);
	
	// 관리자 도서관 aidx를 가지고 오는 매서드
	public int getLibraryBookAboutAidx(int lbidx);
	
	// 관리자 도서관 lbidx, bidx, aidx를 가지고 오는 매서드
	public LibraryBookDto getLibraryBookByLbidxAndBidxAndAidx(int lbidx);
	
	// 관리자 도서관 도서 수정 매서드
	public int modifyLibraryBook(LibraryBooksVo lbv);
}
