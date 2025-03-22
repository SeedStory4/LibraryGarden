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
    public Map<String, Object> getUserLoanInfo(String userNumber) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("서비스 호출됨: userNumber = " + userNumber);

        String userName = adminBookLoanMapper.selectUserName(userNumber);
        System.out.println("회원 이름 조회 결과: " + userName);
        result.put("userName", userName);

        String loanStatus = adminBookLoanMapper.selectUserLoanStatus(userNumber);
        System.out.println("대출 가능 여부 조회 결과: " + loanStatus);
        result.put("loanStatus", loanStatus);

        List<Map<String, Object>> loanList = adminBookLoanMapper.selectUserLoanList(userNumber);
        System.out.println("대출 목록 조회 결과: " + loanList);
        result.put("loanList", loanList);

        return result;
    }

}
