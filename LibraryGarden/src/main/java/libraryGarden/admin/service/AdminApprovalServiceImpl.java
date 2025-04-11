package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import libraryGarden.admin.mapper.AdminApprovalMapper;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminApprovalServiceImpl implements AdminApprovalService{

	@Autowired
	private AdminApprovalMapper lm;

	@Autowired(required=false)
	private AdminBookRequestService bookRequestService;

	@Resource(name = "txManager")
	protected DataSourceTransactionManager txManager;
	
	@Override
	public int approvalTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = lm.approvalTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<ApprovalDto> approvalSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<ApprovalDto> alist = lm.approvalSelectAll(hm);
		
		return alist;
	}
	
	@Override
	public BookVo approvalSelectOne(int aidx) {
		
		BookVo bv = lm.approvalSelectOne(aidx);
		
		return bv;
	};

	@Override
	// 게시글 삭제와 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int approvalDelete(ApprovalVo av, String status) throws Exception{

		if(av.getBidx() == 0) {
			// 삭제한 기안이 희망도서로 등록한 경우 희망도서의 상태를 신청대기로 변경
			int value = bookRequestService.statusUpdate(av.getRqidx(), "신청대기");
		}
		
		// 해당 결재 게시글의 delyn 값 Y로 변경하기
		int cnt = lm.approvalDelete(av.getAidx());
		
		return cnt;
		
	}
	
	@Override
	// 게시글 등록과 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int approvalInsert(ApprovalVo av, String status) throws Exception{
		
		// 희망도서선택으로 기안 등록하는 경우와 도서선택으로 기안 등록하는 경우 데이터가 다르므로 HashMap 사용
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		// uidx 공통
		hm.put("uidx", av.getUidx());
		
		if(av.getBidx() == 0) {
			// 희망도서선택으로 기안 등록시 bidx는 null로 저장
			hm.put("rqidx", av.getRqidx());
			hm.put("bidx", null);

			// 등록할 희망도서의 상태를 신청중으로 변경
			int value2 = bookRequestService.statusUpdate(av.getRqidx(), status);
			
		} else {
			// 도서선택으로 기안 등록시 rqidx는 null로 저장
			hm.put("rqidx", null);
			hm.put("bidx", av.getBidx());
		}

		int value = lm.approvalInsert(hm);
	    
		// 쿼리 반환값이 BigInteger 형식이므로 int 형식으로 변환 필요
		int maxAidx = ((Number) hm.get("maxAidx")).intValue();
		 
		// 게시글 등록 후 상세페이지로 이동하기 위해 방금 추가한 aidx값을 return
		return maxAidx;
		
	};

	@Override
	public ApprovalVo approvalSelectAv(int aidx) {
		
		ApprovalVo av = lm.approvalSelectAv(aidx);
		
		return av;
		
	}

	@Override
	// 게시글 수정과 희망도서 DB 업데이트를 트랜잭션으로 처리. Exception 발생시 롤백
	@Transactional(rollbackFor=Exception.class)
	public int approvalUpdate(ApprovalVo av) throws Exception{
		
		// 희망도서선택으로 기안 등록하는 경우와 도서선택으로 기안 등록하는 경우 데이터가 다르므로 HashMap 사용
		HashMap<String,Object> hm = new HashMap<String,Object>();

		// aidx 공통
		hm.put("aidx", av.getAidx());
		
		// 희망도서로 등록된 기안 수정시 수정전 희망도서 상태를 신청대기로 변경
		ApprovalVo avOrigin = approvalSelectAv(av.getAidx());
		if(avOrigin.getBidx() == 0) {
			int value2 = bookRequestService.statusUpdate(avOrigin.getRqidx(), "신청대기");
		}
		
		if(av.getBidx() == 0) {
			// 희망도서선택으로 기안 등록시 bidx는 null로 저장
			hm.put("rqidx", av.getRqidx());
			hm.put("bidx", null);

			// 등록할 희망도서의 상태를 신청중으로 변경
			int value3 = bookRequestService.statusUpdate(av.getRqidx(), "신청중");
			
		} else {
			// 도서선택으로 기안 등록시 rqidx는 null로 저장
			hm.put("rqidx", null);
			hm.put("bidx", av.getBidx());
		}
		
		// 수정한 게시글 정보를 DB에 반영
		int value = lm.approvalUpdate(hm);

//		if (true) {
//	        // 예외 발생 → 트랜잭션 rollback 테스트
//	        throw new Exception("트랜잭션 롤백 테스트 예외");
//	    }
		
		return value;
	};
}