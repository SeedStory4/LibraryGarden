package libraryGarden.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.domain.BooksVo;
import libraryGarden.user.mapper.BookMapper;


/** [설명] Book1Service 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 * - isbn 으로 책 데이터 여부 확인  메서드
 * - 도서 데이터 삽입 메서드
 * - isbn 으로 책 데이터 중 bidx 가지고 오는 메서드
 *  
 * @author Siyeon
 */
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookMapper bm;
	
	// isbn 으로 도서 데이터 여부 확인 메서드
	@Override
	public int getBookByIsbnToCount(String isbn) {
		int cnt = bm.getBookByIsbnToCount(isbn);
		return cnt;
	}

	// 도서 데이터 삽입 메서드
	@Override
	public int insertBook(BooksVo bv) {
		int bookInsertValue = bm.insertBook(bv);
		return bookInsertValue;
	}

	// isbn 으로 책 데이터 중 bidx 가지고 오는 메서드
	@Override
	public int getBookByIsbnToBidx(String isbn) {
		int bidx = bm.getBookByIsbnToBidx(isbn);
		return bidx;
	}



}
