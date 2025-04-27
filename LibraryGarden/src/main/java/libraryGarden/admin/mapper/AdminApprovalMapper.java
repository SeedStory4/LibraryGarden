package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libraryGarden.domain.ApprovalDto;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.BooksVo;


public interface AdminApprovalMapper {

	/** [설명]AdminApproval2Mapper 인터페이스: 관리자 도서등록을 위한 결재 관련 메서드
	 * 
	 * [주요기능] 
	 *  - 관리자가 승인한 도서 책 갯수 조회 메서드
	 *  - 관리자가 승인한 도서 책 리스트 조회 메서드
	 *  - 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	 *  - 관리자가 승인한 도서 중 등록여부 Y 업데이트 메서드
	 *  - 관리자가 승인한 도서 중 등록여부 N 업데이트 메서드
	 * 
	 * @author SiYeon
	 */
	
	// 관리자가 승인한 도서 책 갯수 조회 메서드
	public int getBookApprovalTotalCount(HashMap<String,Object> hm);
	
	// 관리자가 승인한 도서 책 리스트 조회 메서드
	public List<Map<String, Object>> getBookApprovalSelectAll(HashMap<String,Object> hm);

	// 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	public HashMap<String, Object> getBookApprovalSelectOne(int aidx);
	
	// 관리자가 승인한 도서 중 등록여부 Y 업데이트 메서드
	public int updateApprovalRegynY(int aidx);
	
	// 관리자가 승인한 도서 중 등록여부 N 업데이트 메서드
	public int updateApprovalRegynN(int aidx);
	

	/**
	 * @author JiHye
	 */

	public int getApprovalTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<ApprovalDto> getApprovalSelectAll(HashMap<String,Object> hm);
	
	public BooksVo getApprovalSelectOne(int aidx);

	public int deleteApproval(int aidx);
	
	public int insertApproval(HashMap<String,Object> hm);
	
	public ApprovalVo getApprovalSelectAv(int aidx);
	
	public int updateApproval(HashMap<String,Object> hm);

	public int updateApprovalProcessing(ApprovalVo av);
	
}
