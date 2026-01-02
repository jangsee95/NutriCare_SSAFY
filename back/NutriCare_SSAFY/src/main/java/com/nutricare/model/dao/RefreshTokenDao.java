package com.nutricare.model.dao;

import com.nutricare.model.dto.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenDao {
    // 토큰 저장 (로그인 시)
    int insertToken(RefreshToken token);

    // 사용자 ID로 토큰 조회 (재발급 시 검증용)
    RefreshToken findByUserId(Long userId);

    // 기존 토큰 업데이트 (중복 로그인 처리 등)
    int updateToken(RefreshToken token);

    // 토큰 삭제 (로그아웃 시)
    int deleteByUserId(Long userId);
}