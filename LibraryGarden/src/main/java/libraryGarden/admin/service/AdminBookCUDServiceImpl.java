package libraryGarden.admin.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import libraryGarden.admin.mapper.AdminApproval2Mapper;
import libraryGarden.domain.LibraryBooksVo;


/** [설명] AdminBookCUDService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  - 관리자 도서관 도서 등록 메서드
 *  
 *  
 *  
 * @author Siyeon
 */
@Service
public class AdminBookCUDServiceImpl implements  AdminBookCUDService{

	private static final Logger logger = LoggerFactory.getLogger(AdminBookCUDServiceImpl.class);
	

    @Autowired
    private AdminLibraryBooksService adminLibraryBooksService;
    
    @Autowired
    private AdminApproval2Service adminApprovalService;

	// 도서 등록
	@Override
	// 도서관 도서 등록과 승인된 결재 중 선택한 등록 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
    public int insertLibraryBooksAndUpdateApproval(LibraryBooksVo lbv) {
		
		// 도서 등록
        int insert = adminLibraryBooksService.insertLibraryBookAboutBook(lbv);
        
        int update = adminApprovalService.updateApprovalRegyn(lbv.getAidx());

        if (insert != 0 && update != 0) {
            return insert+update;
        }
        return 0; // 트랜잭션이 실패하면 rollback 됨
    }
	



	
}
