package libraryGarden.user.mapper;

import libraryGarden.domain.BooksVo;

/** [설명]Book1Mapper인터페이스: 사용자 도서 조회 매서드
 * 
 * [주요기능] 
 * - isbn 으로 책 데이터 여부 확인  메서드
 * - 도서 데이터 삽입 메서드
 * - isbn 으로 책 데이터 중 bidx 가지고 오는 메서드
 * 
 * @author SiYeon
 */
public interface BookMapper {
	
	// isbn 으로 도서 데이터 여부 확인 메서드
	public int getBookByIsbnToCount(String isbn);

	// 도서 데이터 삽입 메서드
	public int insertBook(BooksVo bv);
	
	// isbn 으로 책 데이터 중 bidx 가지고 오는 메서드
	public int getBookByIsbnToBidx(String isbn);
}
