package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.Map;

import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;

/**
 * [설명] AdminBookRequestService2 인터페이스: 관리자의 사용자 희망 도서 관리 관련 메서드
 * 
 * [주요기능]
 * - 신청한 희망 도서 책의 갯수(필터 포함)
 * - 신청한 희망 도서 책의 리스트(필터 포함)
 * - 신청한 희망 도서 책 상세
 * - 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
 * 
 * 
 *  
 * @author SiYeon
 * @write 2024.03.23
 * 
 */

public interface AdminBookRequest2Service {
	
	// 희망 도서 신청한 책의 갯수(필터 포함)
	public int getBookRequestTotalCount(SearchCriteria scri, String filter); 
	
	// 희망 도서 신청한 책의 리스트(필터 포함)
	public ArrayList<RequestDto> getBookRequestSelectAll(SearchCriteria scri, String filter);
	
	// 신청한 희망 도서 책 상세
	public Map<String, Object> getBookRequestSelectone(int rqidx);
	
	// 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	public int modifyBookRequest(int rqidx, String status, String rejectionReason);
}
