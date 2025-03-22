package libraryGarden.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import libraryGarden.domain.UserVo;
import libraryGarden.user.mapper.UserMapper;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userMapper")
    private UserMapper userMapper;

    @Override
    @Transactional
    public void insertUser(UserVo user) {
        userMapper.insertUser(user);
    }

    @Override
    public boolean isDuplicateId(String id) {
        return userMapper.countUserById(id) > 0;
    }
    
    @Override
    public String getLastUserNumber() {
        return userMapper.getLastUserNumber();
    }
    
    @Override
    public UserVo login(UserVo userVo) {
        return userMapper.login(userVo);
    }

}
