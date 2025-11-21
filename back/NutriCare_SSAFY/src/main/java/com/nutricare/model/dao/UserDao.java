package com.nutricare.model.dao;

import java.util.List;

import com.nutricare.model.dto.User;

public interface UserDao {
    // 1) 유저 정보 등록 (POST /user)
    int insertUser(User user);

    // 2) 유저 전체 조회 (GET /admin/user)
    List<User> findAllUsers();

    // 3) 유저 상세 조회 (GET /user/me)
    User findUserById(Long userId);

    // 4) 유저 정보 수정 (PATCH /user/me)
    int updateUser(User user);

    // 5) 유저 정보 삭제 (DELETE /user/me)
    int deleteUser(Long userId);

    // 6) 로그인 (POST /user/login)
    User findByEmail(String email);
}
