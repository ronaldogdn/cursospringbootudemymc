package com.ronaldo.curso.dto;

import java.io.Serializable;

import com.ronaldo.curso.domain.Credentials;

import jakarta.validation.constraints.NotEmpty;

public class AuthenticationDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String login;
	@NotEmpty
	private String password;
	
	public AuthenticationDTO() {}
	
	public AuthenticationDTO(Credentials credentials) {
		this.login = credentials.getLogin();
		this.password = credentials.getPassword();		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}