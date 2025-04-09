package libraryGarden.user.mapper;

import org.apache.ibatis.annotations.Param;

/** [설명]BookRequestMapper 인터페이스:  사용자 희망 도서 관련 매서드
 * 
 * [주요기능] 
 * - 희망 도서 신청 (등록) 메서드
 * 
 * 
 * @author SiYeon
 *
 */
public interface BookRequestMapper {
	
	// 희망 도서 신청 (등록) 메서드
	public int insertRequest(@Param("uidx") int uidx,@Param("bidx") int bidx);


}
