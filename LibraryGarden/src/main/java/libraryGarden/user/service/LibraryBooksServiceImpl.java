package libraryGarden.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.domain.LibraryBooksDto;
import libraryGarden.domain.LibraryBooksVo;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.mapper.LibraryBooksMapper;


@Service
public class LibraryBooksServiceImpl implements LibraryBooksService{

	@Autowired
	private LibraryBooksMapper lbm;
	
	/** [설명] LibraryBookService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
	 * 
	 *  [주요기능] 
	 *  - 도서관 책 전체 조회 목록 출력 메서드
	 *  - 도서관 책 전체 갯수 기능(페이징)
	 *  - 도서관 책 상세 조회 기능
	 *  
	 * @author Siyeon
	 */
	
	// 도서관 책 전체 조회 목록 출력 메서드
	@Override
	public ArrayList<LibraryBooksDto> getBookSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());//페이지 조회 첫번째 수
		hm.put("perPageNum", scri.getPerPageNum());//페이지 조회 끝번째 수
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		
		// 책 목록 조회 
		ArrayList<LibraryBooksDto> lblist =  lbm.getBookSelectAll(hm);
		return lblist;
	}

	// 도서관 책 전체 갯수(페이징)
	@Override
	public int getBookTotalCount(SearchCriteria scri) {
		
		 // 페이징 기능 
		int cnt = lbm.getBookTotalCount(scri);
		return cnt;
	}

	// 도서관 책 상세 조회 매서드
	@Override
	public LibraryBooksDto getBookSelectOne(int lbidx) {
		// 도서관 책 상세 조회
		LibraryBooksDto lbd = lbm.getBookSelectOne(lbidx);
		return lbd;
	}
	
	/** 
	 * @author chan
	 */
	
	@Override
	public List<LibraryBooksDto> selectTopLoanBooksThisMonth() {
		return lbm.selectTopLoanBooksThisMonth();
	}

	@Override
	 public List<LibraryBooksDto> getLatestLibraryBooks() {
        return lbm.selectLatestLibraryBooks();
	}
}
