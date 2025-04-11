package libraryGarden.user.mapper;

import org.apache.ibatis.annotations.Param;

import libraryGarden.domain.UserVo;

public interface UserMapper {
	void insertUser(UserVo user);

    int countUserById(String id);
    
    String getLastUserNumber();
    
    UserVo selectUserById(String id);
    
    void updateUser(UserVo user);
    
    String findIdByNameAndPhone(String name, String phone);
    
    String selectIdByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
    
//    public void sendTempPassword(String toEmail, String tempPassword); db에 접근하는게 아니므로 없어도 됨
    
    void updatePasswordByPhone(@Param("id") String id, @Param("phone") String phone, @Param("password") String password);
    
    String findEmailByIdAndPhone(@Param("id") String id, @Param("phone") String phone);


}
