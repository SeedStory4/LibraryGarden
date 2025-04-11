package libraryGarden.user.service;

import java.util.List;
import java.util.Map;


/** [설명]BookRequestService 인터페이스: 사용자 희망 도서 관련 매서드
 * 
 * [주요기능] 
 * - 희망 도서 신청 (등록) 메서드
 * - 사용자 희망 도서 신청을 조회하는 메서드
 * - 사용자 희망 도서 신청한 수를 조회하는 메서드
 * - 사용자 희망 도서 신청을 삭제하는 메서드
 * 
 * @author SiYeon
 *
 */
public interface BookRequestService {

	// 희망 도서 신청 (등록) 메서드
	public int insertRequest(int uidx,int bidx);
	
	// 사용자 희망 도서 신청을 조회하는 메서드
	public List<Map<String, Object>> getUserRequestInfo(int uidx);

	// 사용자 희망 도서 신청한 수를 조회하는 메서드
	public int getUserRequestInfoTotalCount(int uidx);
	
	// 사용자 희망 도서 신청을 삭제하는 메서드
	public int deleteRequest(int uidx,int bidx,int rqidx);

}
