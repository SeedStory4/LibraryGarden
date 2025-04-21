package libraryGarden.admin.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import libraryGarden.domain.BookVo;
import libraryGarden.domain.RequestDto;

public interface AdminBookRequestMapper {

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
	
	// 신청한 희망 도서 책의 갯수(필터 포함)
	public int getBookRequestTotalCount(HashMap<String,Object> hm); 
	
	// 신청한 희망 도서 책의 리스트(필터 포함)
	public ArrayList<RequestDto> getBookRequestSelectAll(HashMap<String,Object> hm); 
	
	// 신청한 희망 도서 책 상세
	public Map<String, Object> getBookRequestSelectone(int rqidx);
	
	// 희망 도서 상세 페이지에서 상태 및 반려 사유 수정
	public int modifyBookRequest(@Param("rqidx") int rqidx, @Param("status") String status, @Param("rejectionReason") String rejectionReason);
	
	
	/**
	 * @author JiHye
	 * @write 2024.03.23
	 */
	
	public int bookRequestTotalCount(HashMap<String,Object> hm);
	
	public ArrayList<RequestDto> bookRequestSelectAll(HashMap<String,Object> hm);

	public BookVo bookRequestSelectOne(int rqidx);

	public int statusUpdate(HashMap<String,Object> hm);
	
}
