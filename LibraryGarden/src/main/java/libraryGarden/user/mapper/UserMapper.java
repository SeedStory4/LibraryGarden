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


}
