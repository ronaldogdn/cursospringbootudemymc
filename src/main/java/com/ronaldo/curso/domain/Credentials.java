package com.ronaldo.curso.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ronaldo.curso.domain.enums.Perfil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credentials implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String login;
	private String password;
	private Perfil role;
	//private Collection<? extends GrantedAuthority> authoritiesPerfis;
	
	public Credentials() {
	}
	
	public Credentials(String login,String password,Perfil role ) {
		this.login = login;
		this.password = password;
		this.role = role;
		//this.authoritiesPerfis = getAuthorities();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == Perfil.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ADMIN"),
					new SimpleGrantedAuthority("CLIENTE"));
		}
		return List.of(new SimpleGrantedAuthority("CLIENTE"));
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/*public Collection<? extends GrantedAuthority> getAuthoritiesPerfis() {
		return authoritiesPerfis;
	}*/
	
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
}