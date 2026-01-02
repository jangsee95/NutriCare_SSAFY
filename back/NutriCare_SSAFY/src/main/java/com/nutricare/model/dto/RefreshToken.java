package com.nutricare.model.dto;

import java.time.LocalDateTime;

public class RefreshToken {
	private Long tokenId;
	private Long userId;
	private String tokenValue;
	private LocalDateTime expiryDate;
	private LocalDateTime createdAt;
	
	public RefreshToken() {
	}

	public RefreshToken(Long userId, String tokenValue, LocalDateTime expiryDate) {
		super();
		this.userId = userId;
		this.tokenValue = tokenValue;
		this.expiryDate = expiryDate;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
