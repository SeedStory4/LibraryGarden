package libraryGarden.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libraryGarden.domain.SearchCriteria;

/** [설명]AdminApproval2Service 인터페이스: 관리자 도서등록을 위한 결재 관련 메서드
 * 
 * [주요기능] 
 *  - 관리자가 승인한 도서 책 갯수 조회 메서드
 *  - 관리자가 승인한 도서 책 리스트 조회 메서드
 *  - 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
 *  - 관리자가 승인한 도서 중 등록시 등록여부 업데이트 메서드
 *  
 *  
 * @author SiYeon
 *
 */
public interface AdminApproval2Service {

	// 관리자가 승인한 도서 책 갯수 조회 메서드
	public int getBookApprovalTotalCount(SearchCriteria scri, String filter, int selectedAidx);
	
	// 관리자가 승인한 도서 책 리스트 조회 메서드
	public List<Map<String, Object>> getBookApprovalSelectAll(SearchCriteria scri, String filter, int selectedAidx);

	// 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	public HashMap<String, Object> getBookApprovalSelectOne(int aidx);
	
	// 관리자가 승인한 도서 중 등록시 등록여부 업데이트 메서드
	public int updateApprovalRegyn(int aidx);

}
