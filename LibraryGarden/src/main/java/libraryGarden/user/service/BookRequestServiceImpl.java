package libraryGarden.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.user.mapper.BookRequestMapper;

/** [설명] BookRequestService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 * - 희망 도서 신청 (등록) 메서드
 * - 사용자 희망 도서 신청을 조회하는 메서드
 * - 사용자 희망 도서 신청한 수를 조회하는 메서드
 * - 사용자 희망 도서 신청을 삭제하는 메서드
 * 
 * 
 * @author Siyeon
 */
@Service
public class BookRequestServiceImpl implements BookRequestService{

	@Autowired
	private BookRequestMapper brm;
	
	// 희망 도서 신청 (등록) 메서드
	@Override
	public int insertRequest(int uidx, int bidx) {
		int requestInsertValue = brm.insertRequest(uidx,bidx);
		return requestInsertValue;
	}

	// 사용자 희망 도서 신청을 조회하는 메서드
	@Override
	public List<Map<String, Object>> getUserRequestInfo(int uidx) {
		List<Map<String, Object>> requestList = brm.getUserRequestInfo(uidx);
		return requestList;
	}

	// 사용자 희망 도서 신청한 수를 조회하는 메서드
	@Override
	public int getUserRequestInfoTotalCount(int uidx) {
		int cnt = brm.getUserRequestInfoTotalCount(uidx);
		return cnt;
	}

	// 사용자 희망 도서 신청을 삭제하는 메서드
	@Override
	public int deleteRequest(int uidx, int bidx, int rqidx) {
		int value = brm.deleteRequest(uidx, bidx, rqidx);
		return value;
	}



}
