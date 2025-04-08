package libraryGarden.user.service;

import java.security.SecureRandom;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	private JavaMailSender mailSender;
	
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
    
    @Override
    public String findIdByNameAndPhone(String name, String phone) {
        return userMapper.selectIdByNameAndPhone(name, phone);
    }
    
    @Override
    public void sendTempPassword(String toEmail, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("LibraryGarden 임시 비밀번호 안내");
        message.setText("임시 비밀번호: " + tempPassword + "\n로그인 후 반드시 비밀번호를 변경해 주세요.");

        mailSender.send(message);
    }
    
    @Override
    public String generateTempPassword() {
        int length = 10;
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charSet.length());
            password.append(charSet.charAt(randomIndex));
        }

        return password.toString();
    }
    
    @Override
    public void updatePasswordByPhone(String id, String phone, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userMapper.updatePasswordByPhone(id, phone, encodedPassword);
    }
    
    @Override
    public String findEmailByIdAndPhone(String id, String phone) {
        return userMapper.findEmailByIdAndPhone(id, phone);
    }





}
