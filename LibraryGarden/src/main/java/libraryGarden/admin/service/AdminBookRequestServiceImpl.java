package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookRequestMapper;
import libraryGarden.domain.BooksVo;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

@Service
public class AdminBookRequestServiceImpl implements AdminBookRequestService{

	@Autowired
	private AdminBookRequestMapper brm;

	
	/**
	 * [설명] AdminBookRequestService : 관리자의 사용자 희망 도서 관리 관련 메서드
	 * 
	 * [주요기능]
	 * - 신청한 희망 도서 책의 갯수(필터 포함)
	 * - 신청한 희망 도서 책의 리스트(필터 포함)
	 * - 신청한 희망 도서 책 상세
	 * - 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	 * 
	 * @author SiYeon
	 * @write 2024.03.23
	 */

	// 희망 도서 신청한 책의 갯수(필터 포함)
	@Override
	public int getBookRequestTotalCount(SearchCriteria scri, String filter) {
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		int cnt = brm.getBookRequestTotalCount(hm);

		return cnt;
	}

	// 희망 도서 신청한 책의 리스트(필터 포함)
	@Override
	public ArrayList<RequestDto> getBookRequestSelectAll(SearchCriteria scri, String filter) {
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		
		ArrayList<RequestDto> rlist = brm.getBookRequestSelectAll(hm);
		
		return rlist;
	}

	// 신청한 희망 도서 책 상세
	@Override
	public Map<String, Object> getBookRequestSelectone(int rqidx) {
		Map<String, Object> rq = brm.getBookRequestSelectone(rqidx);
		return rq;
	}

	// 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	@Override
	public int modifyBookRequest(int rqidx, String status, String rejectionReason) {
		int value = brm.modifyBookRequest(rqidx, status, rejectionReason);
		return value;
	}


	/**
	 * [주요기능]
	 * @author JiHye
	 * @write 2024.03.23
	 */
	
	@Override
	public BooksVo getBookRequestSelectOne(int rqidx) {

		BooksVo bv = brm.getBookRequestSelectOne(rqidx);
		
		return bv;
	};
	
	@Override
	public int updateStatus(int rqidx, String status) {

		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("rqidx", rqidx);
		hm.put("status", status);
		
		int value = brm.updateStatus(hm);
		
		return value;
		
	}

}
