package com.edheijer.WebShopExam.security;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private List<String> roles;
	private String email;

	public JwtResponse(String accessToken) {
		this.token = accessToken;
	}
}
