package com.nutricare.model.service;

import java.util.List;

import com.nutricare.model.dto.User;

public interface UserService {
    // 회원가입
    boolean registerUser(User user);

    // 전체 조회 (admin)
    List<User> getAllUsers();

    // 상세 조회
    User getUserDetail(Long userId);

    // 수정
    boolean updateUser(User user);

    // 삭제
    boolean deleteUser(Long userId);

    // 로그인
    User login(String email, String password);
	
    // 로그아웃 (토큰 기반이면 Service에서 로직 없음)
    void logout(Long userId);
}
