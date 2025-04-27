package libraryGarden.user.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/** [설명]BookRequestMapper 인터페이스:  사용자 희망 도서 관련 매서드
 * 
 * [주요기능] 
 * - 희망 도서 신청 (등록) 메서드
 * - 사용자 희망 도서 신청을 조회하는 메서드
 * - 사용자 희망 도서 신청한 수를 조회하는 메서드
 * - 사용자 희망 도서 신청을 삭제하는 메서드
 * 
 * @author SiYeon
 */
public interface BookRequestMapper {
	
	// 희망 도서 신청 (등록) 메서드
	public int insertRequest(@Param("uidx") int uidx,@Param("bidx") int bidx);

	// 사용자 희망 도서 신청을 조회하는 메서드
	public List<Map<String, Object>> getUserRequestInfo(HashMap<String,Object> hm);
	
	// 사용자 희망 도서 신청한 수를 조회하는 메서드
	public int getUserRequestInfoTotalCount(int uidx);
	
	// 사용자 희망 도서 신청을 삭제하는 메서드
	public int deleteRequest(@Param("uidx") int uidx,@Param("bidx") int bidx,@Param("rqidx") int rqidx);
}
