package libraryGarden.admin.mapper;

import java.util.List;
 
import libraryGarden.domain.LibraryBookDto;
 

/** [설명]AdminCategoryMapper 인터페이스: 관리자가 사용하는 도서 카테고리 관련 메서드
 * 
 * [주요기능] 
 *  - 관리자 도서 등록/수정을 위한 카테고리 중 대분류 가지고 오는 메서드
 *  - 관리자 도서 등록/수정을 위한 카테고리 중 소분류 가지고 오는 메서드
 *  
 * @author SiYeon
 */
public interface AdminCategoryMapper {
	
	// 관리자 도서 등록/수정을 위한 카테고리 중 대분류 가지고 오는 메서드
	public List<LibraryBookDto> getParentCategoryByLevel(int level);
	
    // 관리자 도서 등록/수정을 위한 카테고리 중 소분류 가지고 오는 메서드
	public List<LibraryBookDto> getChildrenCategoryByparentCode(int parentCode);
}
