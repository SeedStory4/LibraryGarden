package libraryGarden.admin.service;

import libraryGarden.domain.LibraryBooksVo;


/** [설명]AdminBookCUDService 인터페이스: 관리자 도서 CUD를 위한 서비스
 * 
 * [주요기능] 
 *  - 관리자 도서관 도서 등록 메서드
 *  - 관리자 도서관 도서 삭제 메서드
 *  
 *  
 * @author SiYeon
 *
 */
public interface AdminBookCUDService {

	// 관리자 도서관 도서 등록 메서드
	public int insertLibraryBooksAndUpdateApproval(LibraryBooksVo lbv);
	
	// 관리자 도서관 도서 삭제 메서드
	public int deleteLibraryBooksAndUpdateApproval(int lbidx);

}
