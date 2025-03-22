package libraryGarden.user.mapper;

import libraryGarden.domain.UserVo;

public interface UserMapper {
	void insertUser(UserVo user);

    int countUserById(String id);

}
