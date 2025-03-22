package libraryGarden.user.service;

import libraryGarden.domain.UserVo;

public interface UserService {
	
	 // 회원 가입 처리
    void insertUser(UserVo user);

    // 아이디 중복 확인
    boolean isDuplicateId(String id);
    
    // 마지막 유저넘버가져오기
    String getLastUserNumber();
    
    UserVo login(UserVo userVo);

}
