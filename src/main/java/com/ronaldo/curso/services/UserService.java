package com.ronaldo.curso.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ronaldo.curso.domain.Credentials;

public class UserService {

	public static Credentials authenticated() {
		try {
			return (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}