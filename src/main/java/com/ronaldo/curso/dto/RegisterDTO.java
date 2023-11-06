package com.ronaldo.curso.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.ronaldo.curso.domain.enums.Perfil;

import jakarta.validation.constraints.NotEmpty;

public class RegisterDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5,max = 20,message = "O tamanho deve ser entre 5 e 20 caracteres")
	private String login; 
	@NotEmpty(message = "Preenchimento obrigatório")
	private String password;
	
	private Perfil role;
		
	/*public RegisterDTO(String login,String password,String role) {
		this.login = login;
		this.password = password;
		this.role = role.equals("admin") ? Perfil.ADMIN : Perfil.CLIENTE;
	}*/
	public RegisterDTO() {}
	
	public RegisterDTO(RegisterDTO credentials) {
		this.login = credentials.getLogin();
		this.password = credentials.getPassword();
		this.role = Perfil.ADMIN;
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
	public Perfil getRole() {
		return role;
	}
	public void setRole(Perfil role) {
		this.role = role;
	}
}