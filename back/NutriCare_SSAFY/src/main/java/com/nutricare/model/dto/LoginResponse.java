package com.nutricare.model.dto;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private Long userId;

    public LoginResponse(String token, String refreshToken, Long userId) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
    

}
