package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import libraryGarden.admin.mapper.AdminApprovalMapper;
import libraryGarden.cmm.util.AladdinOpenAPI;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.service.BookService;


@Service
public class AdminApprovalServiceImpl implements  AdminApprovalService{

	@Autowired
	private AdminApprovalMapper am;

	@Autowired(required=false)
	private AdminBookRequestService bookRequestService;
	
	@Autowired(required = false)
	AladdinOpenAPI aladdinOpenAPI;
	
	@Autowired(required=false)
	BookService bookService;
	
	/** [설명] AdminApproval2Service 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
	 * 
	 *  [주요기능] 
	 *  - 관리자가 승인한 도서 책 갯수 조회 메서드
	 *  - 관리자가 승인한 도서 책 리스트 조회 메서드
	 *  - 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	 *  - 관리자가 승인한 도서 중 등록여부 Y 업데이트 메서드
	 *  - 관리자가 승인한 도서 중 등록여부 N 업데이트 메서드
	 *  
	 * @author Siyeon
	 */
	
	// 관리자가 승인한 도서 책 갯수 조회 메서드
	@Override
	public int getBookApprovalTotalCount(SearchCriteria scri, String filter, int selectedAidx) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		hm.put("selectedAidx", selectedAidx);
		
		int cnt = am.getBookApprovalTotalCount(hm);
		return cnt;
	}

	// 관리자가 승인한 도서 책 리스트 조회 메서드
	@Override
	public List<Map<String, Object>> getBookApprovalSelectAll(SearchCriteria scri, String filter, int selectedAidx) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		hm.put("selectedAidx", selectedAidx);
		
		List<Map<String, Object>> blist = am.getBookApprovalSelectAll(hm);
		
		return blist;
	}

	// 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	@Override
	public HashMap<String, Object>  getBookApprovalSelectOne(int aidx) {
		HashMap<String, Object> hm = am.getBookApprovalSelectOne(aidx);
		return hm;
	}

	// 관리자가 승인한 도서 중 등록여부 Y 업데이트 메서드
	@Override
	public int updateApprovalRegynY(int aidx) {
		int cnt = am.updateApprovalRegynY(aidx);
		return cnt;
	}

	// 관리자가 승인한 도서 중 등록여부 N 업데이트 메서드
	@Override
	public int updateApprovalRegynN(int aidx) {
		int cnt = am.updateApprovalRegynN(aidx);
		return cnt;
	}
	

	/** 
	 * @author JiHye
	 */
	@Override
	public int getApprovalTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = am.getApprovalTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<ApprovalDto> getApprovalSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<ApprovalDto> alist = am.getApprovalSelectAll(hm);
		
		return alist;
	}
	
	@Override
	public BooksVo getApprovalSelectOne(int aidx) {
		
		BooksVo bv = am.getApprovalSelectOne(aidx);
		
		return bv;
	};

	@Override
	// 게시글 삭제와 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int deleteApproval(ApprovalVo av, String status) throws Exception{

		if(av.getRqidx() != 0) {
			// 삭제한 기안이 희망도서로 등록한 경우 희망도서의 상태를 신청대기로 변경
			int value = bookRequestService.updateStatus(av.getRqidx(), status);
		}
		
		// 해당 결재 게시글의 delyn 값 Y로 변경하기
		int cnt = am.deleteApproval(av.getAidx());
		
		return cnt;
		
	}
	
	@Override
	// 게시글 등록과 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int insertApproval(ApprovalVo av, BooksVo bv, String status) throws Exception{
		
		// 희망도서선택으로 기안 등록하는 경우와 도서선택으로 기안 등록하는 경우 데이터가 다르므로 HashMap 사용
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		// uidx 공통
		hm.put("uidx", av.getUidx());
		
		if(av.getRqidx() != 0) {
			// 희망도서선택으로 기안 등록시 bidx는 null로 저장
			hm.put("rqidx", av.getRqidx());
			hm.put("bidx", null);

			// 등록할 희망도서의 상태를 신청중으로 변경
			int value2 = bookRequestService.updateStatus(av.getRqidx(), status);
			
		} else {
			// 도서선택으로 기안 등록하는 경우 도서 DB에 도서 정보 저장 필요(단, 이미 등록된 경우 생략)
		    // isbn으로 Book 테이블에 책이 저장되어있는지 확인
		    String isbn =  bv.getIsbn();
		    int cnt = bookService.getBookByIsbnToCount(isbn);
		    
		    if(cnt == 0) {
		    	// Book 테이블에 책이 없을 경우 알라딘 API에서 isbn으로 도서정보 가져와서 등록
			    bv = aladdinOpenAPI.lookUpBookDetail(isbn);
		    	int bookInsertValue = bookService.insertBook(bv);
		    }

	        // isbn으로 도서 bidx를 찾아서 av에 저장
	        int bidx = bookService.getBookByIsbnToBidx(isbn);
		    
			// 도서선택으로 기안 등록시 rqidx는 null로 저장
			hm.put("rqidx", null);
			hm.put("bidx", bidx);
		}

		int value = am.insertApproval(hm);
	    
		// 쿼리 반환값이 BigInteger 형식이므로 int 형식으로 변환 필요
		int maxAidx = ((Number) hm.get("maxAidx")).intValue();
		 
		// 게시글 등록 후 상세페이지로 이동하기 위해 방금 추가한 aidx값을 return
		return maxAidx;
		
	};

	@Override
	public ApprovalVo getApprovalSelectAv(int aidx) {
		
		ApprovalVo av = am.getApprovalSelectAv(aidx);
		
		return av;
		
	}

	@Override
	// 게시글 수정과 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int updateApproval(ApprovalVo av, BooksVo bv) throws Exception{
		
		// 희망도서선택으로 기안 등록하는 경우와 도서선택으로 기안 등록하는 경우 데이터가 다르므로 HashMap 사용
		HashMap<String,Object> hm = new HashMap<String,Object>();

		// aidx 공통
		hm.put("aidx", av.getAidx());
		
		// 희망도서로 등록된 기안 수정시 수정전 희망도서 상태를 신청대기로 변경
		ApprovalVo avOrigin = getApprovalSelectAv(av.getAidx());
		if(avOrigin.getRqidx() != 0) {
			int value2 = bookRequestService.updateStatus(avOrigin.getRqidx(), "신청대기");
		}
		
		if(av.getRqidx() != 0) {
			// 희망도서선택으로 기안 등록시 bidx는 null로 저장
			hm.put("rqidx", av.getRqidx());
			hm.put("bidx", null);

			// 등록할 희망도서의 상태를 신청중으로 변경
			int value3 = bookRequestService.updateStatus(av.getRqidx(), "신청중");
			
		} else {
			// 도서선택으로 기안 등록시 rqidx는 null로 저장
			hm.put("rqidx", null);
			hm.put("bidx", av.getBidx());
			
			
			// 도서선택으로 기안 수정하는 경우 도서 DB에 도서 정보 저장 필요(단, 이미 등록된 경우 생략)
		    // isbn으로 Book 테이블에 책이 저장되어있는지 확인
		    String isbn =  bv.getIsbn();
		    int cnt = bookService.getBookByIsbnToCount(isbn);
		    
		    if(cnt == 0) {
		    	// Book 테이블에 책이 없을 경우 알라딘 API에서 isbn으로 도서정보 가져와서 등록
			    bv = aladdinOpenAPI.lookUpBookDetail(isbn);
		    	int bookInsertValue = bookService.insertBook(bv);
		    }

	        // isbn으로 도서 bidx를 찾아서 av에 저장
	        int bidx = bookService.getBookByIsbnToBidx(isbn);
		    
			// 도서선택으로 기안 등록시 rqidx는 null로 저장
			hm.put("rqidx", null);
			hm.put("bidx", bidx);
			
		}
		
		// 수정한 게시글 정보를 DB에 반영
		int value = am.updateApproval(hm);

//		if (true) {
//	        // 예외 발생 → 트랜잭션 rollback 테스트
//	        throw new Exception("트랜잭션 롤백 테스트 예외");
//	    }
		
		return value;
	};
	
	// 게시글 승인/반려처리와 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int updateApprovalProcessing(ApprovalVo av) {
		
		// requestStatus, requestRejectionReason 변경을 위해 aidx로 rqidx를 확인 후 저장
		ApprovalVo avOrigin = getApprovalSelectAv(av.getAidx());
		av.setRqidx(avOrigin.getRqidx());
		
		// requestStatus 초기화
		String requestStatus;
		
		if(av.getRejectionReason() != null) {
			
			// 결재 반려
			av.setStatus("반려");
			
			// 희망도서로 등록된 기안 반려시 희망도서 상태를 신청반려로 변경
			requestStatus = "신청반려";
			
		} else {
			
			// 결재 승인
			av.setStatus("승인");

			// 희망도서로 등록된 기안 승인시 희망도서 상태를 신청완료로 변경
			requestStatus = "신청완료";
			
		}
		
		// 희망도서 정보를 DB에 반영
		if(av.getRqidx() != 0) {
			int value = bookRequestService.modifyBookRequest(avOrigin.getRqidx(), requestStatus, av.getRejectionReason());
		}

		// 결재 정보를 DB에 반영
		int value = am.updateApprovalProcessing(av);
		
		return value;
		
	};
	
}