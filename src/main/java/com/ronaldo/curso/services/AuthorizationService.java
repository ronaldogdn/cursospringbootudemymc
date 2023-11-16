package com.ronaldo.curso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ronaldo.curso.repositories.CredentialsRepository;

@Service
public class AuthorizationService implements UserDetailsService{
	/**
	 * Classe responsável pela autenticação dos usuários
	 */
	@Autowired
	CredentialsRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = repository.findByLogin(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
}