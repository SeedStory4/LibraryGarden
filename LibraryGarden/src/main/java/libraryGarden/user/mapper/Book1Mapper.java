package libraryGarden.user.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.SearchCriteria;

/** [설명]Book1Mapper 인터페이스: 책 관련 기능의 메서드 정의
 * 
 * [주요기능] 
 * - 도서관 책 전체 조회 목록 출력 메서드
 * - 도서관 책 전체 갯수 매서드
 * - 도서관 책 상세 조회 매서드
 * 
 * @author SiYeon
 *
 */
public interface Book1Mapper {
	
	// 도서관 책 전체 조회 목록 출력 메서드
	public ArrayList<LibraryBookDto> BookSelectAll(HashMap<String,Object> hm);

	// 도서관 책 전체 갯수 매서드
	int BookTotalCount(SearchCriteria scri);
	
	// 도서관 책 상세 조회 매서드
	public LibraryBookDto BookSelectOne(int lbidx);
}
