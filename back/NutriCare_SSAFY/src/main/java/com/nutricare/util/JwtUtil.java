package com.nutricare.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key key;

    private final long ACCESS_EXPIRATION = 1000 * 60; // 30분
    private final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    /**
     * JwtUtil은 오직 환경변수 `JWT_SECRET`만 사용합니다.
     * (개발/운영 환경에서는 이 환경변수를 설정해 주세요)
     */
    public JwtUtil(@Value("${JWT_SECRET:}") String secret) {
        if (secret == null || secret.isBlank() || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("Environment variable JWT_SECRET must be provided and at least 32 bytes long");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateRefreshToken(Long userId) {
    	return Jwts.builder()
    			.setSubject(String.valueOf(userId))
    			.setIssuedAt(new Date())
    			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
    			.signWith(key, SignatureAlgorithm.HS256)
    			.compact();
    }

    // 1) 토큰 생성
    public String generateAccessToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2) userId 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return Long.valueOf(claims.getSubject());
    }
    
    // 3) email 추출
    public String getEmailFromToken(String token) {
    	Claims claims = getClaims(token);
    	return (String) claims.get("email");
    }

    // 4) role 추출
    public String getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("role");
    }

    // 5) Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	public long getACCESS_EXPIRATION() {
		return ACCESS_EXPIRATION;
	}
	public long getREFRESH_EXPIRATION() {
		return REFRESH_EXPIRATION;
	}
    
    
}
