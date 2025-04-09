package libraryGarden.user.service;


/** [설명]BookRequestService 인터페이스: 사용자 희망 도서 관련 매서드
 * 
 * [주요기능] 
 * - 희망 도서 신청 (등록) 메서드
 * 
 * @author SiYeon
 *
 */
public interface BookRequestService {

	// 희망 도서 신청 (등록) 메서드
	public int insertRequest(int uidx,int bidx);

}
