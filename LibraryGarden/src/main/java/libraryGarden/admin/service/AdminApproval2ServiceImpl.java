package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminApproval2Mapper;
import libraryGarden.admin.mapper.AdminBookMapper;
import libraryGarden.domain.BookVo;
import libraryGarden.domain.LibraryBookDto;
import libraryGarden.domain.RequestDto;
import libraryGarden.domain.SearchCriteria;
import libraryGarden.user.mapper.LibraryBooksMapper;

/** [설명] AdminApproval2Service 인터페이스를 구현한 클래스 - 비즈니스 로직을 처리
 * 
 *  [주요기능] 
 *  - 관리자가 승인한 도서 책 갯수 조회 메서드
 *  - 관리자가 승인한 도서 책 리스트 조회 메서드
 *  - 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
 *  
 * @author Siyeon
 */
@Service
public class AdminApproval2ServiceImpl implements  AdminApproval2Service{

	private static final Logger logger = LoggerFactory.getLogger(AdminApproval2ServiceImpl.class);
	
	@Autowired
	private AdminApproval2Mapper aam;
	

	// 관리자가 승인한 도서 책 갯수 조회 메서드
	@Override
	public int getBookApprovalTotalCount(SearchCriteria scri, String filter, int selectedAidx) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		hm.put("selectedAidx", selectedAidx);
		
		int cnt = aam.getBookApprovalTotalCount(hm);
		return cnt;
	}

	// 관리자가 승인한 도서 책 리스트 조회 메서드
	@Override
	public List<Map<String, Object>> getBookApprovalSelectAll(SearchCriteria scri, String filter, int selectedAidx) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("filter", filter);
		hm.put("selectedAidx", selectedAidx);
		
		List<Map<String, Object>> blist = aam.getBookApprovalSelectAll(hm);
		
		return blist;

	}

	// 관리자가 승인한 도서 중 등록할 책 정보 조회 메서드
	@Override
	public BookVo getBookApprovalSelectOne(int aidx) {
		BookVo bv = aam.getBookApprovalSelectOne(aidx);
		return bv;
	}
	
}
