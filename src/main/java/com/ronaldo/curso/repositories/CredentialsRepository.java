package com.ronaldo.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.ronaldo.curso.domain.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer>{
	UserDetails findByLogin(String login);
}