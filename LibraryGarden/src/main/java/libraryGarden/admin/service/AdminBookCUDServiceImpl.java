package libraryGarden.admin.service;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import libraryGarden.domain.LibraryBooksVo;


/** [설명] AdminBookCUDService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  - 도서관 도서 등록 메서드
 *  - 도서관 도서 삭제 메서드
 *  - 도서관 도서 수정 메서드
 *  
 * @author Siyeon
 */
@Service
public class AdminBookCUDServiceImpl implements  AdminBookCUDService{
 
    @Autowired
    private AdminLibraryBooksService adminLibraryBooksService;
    
    @Autowired
    private AdminApprovalService adminApprovalService;

	// 도서관 도서 등록 메서드
	@Override
	// 도서관 도서 등록과 승인된 등록된 도서 등록 여부 수정 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
    public int insertLibraryBooksAndUpdateApproval(LibraryBooksVo lbv) {
		
		// 도서 등록
        int insert = adminLibraryBooksService.insertLibraryBookAboutBook(lbv);
        
        // 등록된 도서 등록여부 Y, 수정날짜 변경
        int update = adminApprovalService.updateApprovalRegynY(lbv.getAidx());

        if (insert != 0 && update != 0) {
            return insert+update;
        }
        return 0; // 트랜잭션이 실패하면 rollback 됨
    }

	// 도서관 도서 삭제 메서드
	@Override
	// 도서관 도서 삭제와 삭제된 도서 등록 여부 수정 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int deleteLibraryBooksAndUpdateApproval(int lbidx) {
		
		// aidx 가지고 오기 
		int aidx = adminLibraryBooksService.getLibraryBookAboutAidx(lbidx);
		
		// 도서 삭제 삭제여부 Y, 수정날짜 변경
		int libraryBookValue = adminLibraryBooksService.getLibraryBookDeleteOne(lbidx);
		
        // 등록된 도서 등록여부 N, 수정날짜 변경
        int approvalValue = adminApprovalService.updateApprovalRegynN(aidx);
        
        if (libraryBookValue != 0 && approvalValue != 0) {
            return libraryBookValue+libraryBookValue;
        }
		return 0;
	}

    // 도서관 도서 수정 메서드
	@Override
	// 도서관 도서 수정과 수정된 도서 등록 여부 수정 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int modifyLibraryBooksAndUpdateApproval(LibraryBooksVo lbv) {
		
		int value  = adminLibraryBooksService.modifyLibraryBook(lbv);  // 수정
	    return value;
	}
	
}
