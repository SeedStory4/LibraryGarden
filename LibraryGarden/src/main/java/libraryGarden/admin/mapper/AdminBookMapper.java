package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libraryGarden.domain.BookVo;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.SearchCriteria;

/** [설명]AdminBookMapper 인터페이스: 관리자 사용자 도서관 책 관련 메서드
 * 
 * [주요기능] 
 *  - 관리자 도서관 책 전체 조회 목록 출력 메서드
 *  - 관리자 도서관 책 전체 갯수 매서드(페이징)
 *  - 관리자 도서관 책 상세 조회 매서드
 *  - 관리자 도서관 책 삭제 매서드
 *  - 관리자가 승인한 도서 책 갯수 조회 메서드
 *  - 관리자가 승인한 도서 책 리스트 조회 메서드
 *  - 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
 * 
 * @author SiYeon
 *
 */
public interface AdminBookMapper {
	
	// 관리자 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBookDto> BookSelectAll(HashMap<String,Object> hm);

	// 관리자 도서관 책 전체 갯수 매서드
	int BookTotalCount(SearchCriteria scri);
	
	// 관리자 도서관 책 상세 조회 매서드
	public LibraryBookDto BookSelectOne(int lbidx);
	
	// 관리자 도서관 책 삭제 매서드
	public int BookDeleteOne(int lbidx);
	
	// 관리자가 승인한 도서 책 갯수 조회 메서드
	public int bookApprovalTotalCount(HashMap<String,Object> hm);
	
	// 관리자가 승인한 도서 책 리스트 조회 메서드
	public List<Map<String, Object>> bookApprovalSelectAll(HashMap<String,Object> hm);

	// 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	public BookVo bookApprovalSelectOne(int aidx);
}
