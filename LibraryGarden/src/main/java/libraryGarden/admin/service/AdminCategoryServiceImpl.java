package libraryGarden.admin.service;

 
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import libraryGarden.admin.mapper.AdminCategoryMapper;
import libraryGarden.domain.LibraryBookDto;
 

/** [설명] AdminCategoryService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 * [주요기능] 
 *  - 관리자 도서 등록/수정을 위한 카테고리 중 대분류 가지고 오는 메서드
 *  - 관리자 도서 등록/수정을 위한 카테고리 중 소분류 가지고 오는 메서드
 *  
 *  
 *  
 *  
 *  
 * @author Siyeon
 */
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService{

	private static final Logger logger = LoggerFactory.getLogger(AdminCategoryServiceImpl.class);
	
	@Autowired
	private AdminCategoryMapper acm;

	// 관리자 도서 등록/수정을 위한 카테고리 중 대분류 가지고 오는 메서드
	@Override
	public List<LibraryBookDto> getParentCategoryByLevel() {
		return acm.getParentCategoryByLevel(1);
	}

	// 관리자 도서 등록/수정을 위한 카테고리 중 소분류 가지고 오는 메서드
	@Override
	public List<LibraryBookDto> getChildrenCategoryByparentCode(int parentCode) {
		return acm.getChildrenCategoryByparentCode(parentCode);
	}
	


	
}
