package libraryGarden.user.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import libraryGarden.domain.SearchCriteria;
import libraryGarden.domain.UserVo;

public interface UserService {
	
	 // 회원 가입 처리
    void insertUser(UserVo user);

    // 아이디 중복 확인
    boolean isDuplicateId(String id);
    
    // 마지막 유저넘버가져오기
    String getLastUserNumber();
    
    UserVo login(UserVo userVo);
    
    // 아이디 유효성검사
    int countUserById(String id);
    
    UserVo selectUserById(String id);
    
    void updateUser(UserVo user);
    
    String findIdByNameAndPhone(String name, String phone);

    void sendTempPassword(String toEmail, String tempPassword);
    
    String generateTempPassword();
    
    void updatePasswordByPhone(@Param("id") String id, @Param("phone") String phone, @Param("password") String password);

    String findEmailByIdAndPhone(String id, String phone);
    
    List<UserVo> selectAllUsers();
    
    List<UserVo> searchUsersByCriteria(SearchCriteria cri);
    
    int countUsers(SearchCriteria cri);
    
    void updateAdminUser(UserVo user);



}
