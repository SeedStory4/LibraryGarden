package libraryGarden.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.LibrarianApprovalMapper;
import libraryGarden.domain.ApprovalVo;
import libraryGarden.domain.SearchCriteria;

@Service
public class LibrarianApprovalServiceImpl implements LibrarianApprovalService{
	
	private LibrarianApprovalMapper lm;

	@Autowired
	public LibrarianApprovalServiceImpl(SqlSession sqlSession) {
		this.lm = sqlSession.getMapper(LibrarianApprovalMapper.class);
	}

	@Override
	public int librarianApprovalTotalCount(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		int cnt = lm.librarianApprovalTotalCount(hm);
		
		return cnt;
	}
	
	@Override
	public ArrayList<ApprovalVo> librarianApprovalSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		ArrayList<ApprovalVo> alist = lm.librarianApprovalSelectAll(hm);
		
		return alist;
	}
	
	
	
}
