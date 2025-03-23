package libraryGarden.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.admin.mapper.AdminBookLoanMapper;

@Service
public class AdminBookLoanServiceImpl implements AdminBookLoanService{
	
    @Autowired
    private AdminBookLoanMapper adminBookLoanMapper;
    
    
    @Override
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) {
        Map<String, Object> result = new HashMap<>();
        //System.out.println("서비스 호출됨: userNumber = " + userNumber);

        String userName = adminBookLoanMapper.selectUserName(userNumber);
        //System.out.println("회원 이름 조회 결과: " + userName);
        result.put("userName", userName);

        String loanStatus = adminBookLoanMapper.selectUserLoanStatus(userNumber);
        //System.out.println("대출 가능 여부 조회 결과: " + loanStatus);
        result.put("loanStatus", loanStatus);

        Map<String, Object> params = new HashMap<>();
        int startPageNum = (page - 1) * perPageNum;
        params.put("userNumber", userNumber);
        params.put("startPageNum", startPageNum);
        params.put("perPageNum", perPageNum);

        List<Map<String, Object>> loanList = adminBookLoanMapper.selectUserLoanList(params);
        int totalCount = adminBookLoanMapper.selectUserLoanTotalCount(userNumber);
        
        result.put("loanList", loanList);
        result.put("totalCount", totalCount);

        return result;
    }

}
