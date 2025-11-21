package com.nutricare.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutricare.model.dao.UserDao;
import com.nutricare.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // 1) 회원가입
    @Override
    public boolean registerUser(User user) {
        String hashed = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashed);

        return userDao.insertUser(user) > 0;
    }

    // 2) 전체조회
    @Override
    public List<User> getAllUsers() {
        return userDao.findAllUsers();
    }

    // 3) 상세조회
    @Override
    public User getUserDetail(Long userId) {
        return userDao.findUserById(userId);
    }

    // 4) 수정
    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user) > 0;
    }

    // 5) 삭제
    @Override
    public boolean deleteUser(Long userId) {
        return userDao.deleteUser(userId) > 0;
    }

    // 6) 로그인
    @Override
    public User login(String email, String password) {

        // 1) email로 사용자 조회
        User user = userDao.findByEmail(email);
        if (user == null) return null;

        // 2) 입력된 비밀번호(raw) vs DB 해시 비교
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return null;
        }

        // 3) 성공시 user 정보 반환
        return user;
    }
    
    // 7) 로그아웃
    @Override
    public void logout(Long userId) {
        // JWT를 사용한다면 서버에서는 특별한 동작 없음 -> 추후 고민할 내용
    }
}
