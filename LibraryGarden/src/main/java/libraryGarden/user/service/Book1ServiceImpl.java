package libraryGarden.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.domain.BookVo;
import libraryGarden.user.mapper.Book1Mapper;


/** [설명] Book1Service 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 * - isbn 으로 책 데이터 여부 확인  메서드
 * - 도서 데이터 삽입 메서드
 * - isbn 으로 책 데이터 중 bidx 가지고 오는 메서드
 *  
 *  
 * @author Siyeon
 */
@Service
public class Book1ServiceImpl implements Book1Service{

	@Autowired
	private Book1Mapper bm;
	
	// isbn 으로 도서 데이터 여부 확인 메서드
	@Override
	public int findBookByIsbnToCount(String isbn) {
		int cnt = bm.findBookByIsbnToCount(isbn);
		return cnt;
	}

	// 도서 데이터 삽입 메서드
	@Override
	public int insertBook(BookVo bv) {
		int bookInsertValue = bm.insertBook(bv);
		return bookInsertValue;
	}

	// isbn 으로 책 데이터 중 bidx 가지고 오는 메서드
	@Override
	public int findBookByIsbnToBidx(String isbn) {
		int bidx = bm.findBookByIsbnToBidx(isbn);
		return bidx;
	}



}
