package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminDirectorApprovalMapper;
import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminDirectorApprovalServiceImpl implements AdminDirectorApprovalService{

	@Autowired
	private AdminDirectorApprovalMapper dm;

	@Override
	public int directorApprovalTotalCount(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = dm.directorApprovalTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<ApprovalDto> directorApprovalSelectAll(SearchCriteria scri, String filter) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<ApprovalDto> alist = dm.directorApprovalSelectAll(hm);
		
		return alist;
	}
	
	@Override
	public BookVo directorApprovalSelectOne(int aidx){
		
		BookVo bv = dm.directorApprovalSelectOne(aidx);
		
		return bv;	
	};

	@Override
	public int directorApprovalDelete(int aidx) {
		
		int cnt = dm.directorApprovalDelete(aidx);
		
		return cnt;
		
	}

	@Override
	public int approvalInsert(ApprovalVo av) {

		// 희망도서선택으로 기안 등록하는 경우와 도서선택으로 기안 등록하는 경우 데이터가 다르므로 HashMap 사용
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		// uidx 공통
		hm.put("uidx", av.getUidx());
		
		// 희망도서선택으로 기안 등록시 bidx는 null로 저장
		if(av.getBidx() == 0) {
			hm.put("rqidx", av.getRqidx());
			hm.put("bidx", null);
			
		// 도서선택으로 기안 등록시 rqidx는 null로 저장
		} else {
			hm.put("rqidx", null);
			hm.put("bidx", av.getBidx());
		}
		
		int value = dm.approvalInsert(hm);
		
		// 쿼리 반환값이 BigInteger 형식이므로 int 형식으로 변환 필요
		int maxAidx = ((Number) hm.get("maxAidx")).intValue();
		
		// 게시글 등록 후 상세페이지로 이동하기 위해 방금 추가한 aidx값을 return
		return maxAidx;
	};
}
