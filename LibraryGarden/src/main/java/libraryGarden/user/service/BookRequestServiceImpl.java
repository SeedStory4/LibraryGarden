package libraryGarden.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.user.mapper.BookRequestMapper;

/** [설명] BookRequestService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 * - 희망 도서 신청 (등록) 메서드
 *  
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



}
