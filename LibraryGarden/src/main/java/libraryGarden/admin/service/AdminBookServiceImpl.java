package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import libraryGarden.admin.mapper.AdminBookMapper;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.mapper.LibraryBooksMapper;

/** [설명] AdminBookService 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  
 * @author Siyeon
 */
@Service
public class AdminBookServiceImpl implements AdminBookService{

	private static final Logger logger = LoggerFactory.getLogger(AdminBookServiceImpl.class);

	
}
