package libraryGarden.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import libraryGarden.user.mapper.MyPage1Mapper;

@Service
public class MyPage1ServiceImpl implements MyPage1Service{
	
    @Autowired
    private MyPage1Mapper myPage1Mapper;

    @Override
    public String getUserLoanStatus(String userNumber) throws Exception {
        return myPage1Mapper.selectUserLoanStatus(userNumber); // 대출 가능 여부 조회
    }

    @Override
    public Map<String, Object> getUserLoanInfo(String userNumber, int page, int perPageNum) throws Exception {
        Map<String, Object> result = new HashMap<>();

        // 페이징 계산 추가
        int startPageNum = (page - 1) * perPageNum;

        List<Map<String, Object>> loanList = myPage1Mapper.selectUserLoanList(userNumber, startPageNum, perPageNum);

        int totalCount = myPage1Mapper.selectUserLoanTotalCount(userNumber);

        result.put("loanList", loanList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public String getUserName(String userNumber) throws Exception {
        return myPage1Mapper.selectUserName(userNumber); // 회원 이름 조회
    }
    
    
    @Override
    public Map<String, Object> getUserReservationInfo(String userNumber, int page, int perPageNum) throws Exception {
        Map<String, Object> result = new HashMap<>();

        // 페이징 계산 추가
        int startPageNum = (page - 1) * perPageNum;

        List<Map<String, Object>> reservationList = myPage1Mapper.selectUserReservationList(userNumber, startPageNum, perPageNum);

        int totalCount = myPage1Mapper.selectUserReservationTotalCount(userNumber);

        result.put("reservationList", reservationList);
        result.put("totalCount", totalCount);

        return result;
    }

}
