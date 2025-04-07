package libraryGarden.user.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import libraryGarden.domain.UserVo;
import libraryGarden.user.mapper.UserMapper;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userMapper")
    private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void insertUser(UserVo user) {
    	 // 비밀번호 암호화 후 저장
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
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
    @Transactional(readOnly = true)
    public UserVo login(UserVo userVo) {
        // DB에서 ID로 사용자 정보 조회
        UserVo user = userMapper.selectUserById(userVo.getId());

        if (user != null && passwordEncoder.matches(userVo.getPassword(), user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
    
    @Override
    public int countUserById(String id) {
        return userMapper.countUserById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserVo selectUserById(String id) {
        return userMapper.selectUserById(id);
    }
    
    @Override
    public void updateUser(UserVo user) {
        userMapper.updateUser(user);
    }



}
