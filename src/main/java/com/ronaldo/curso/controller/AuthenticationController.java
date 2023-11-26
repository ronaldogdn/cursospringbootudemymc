package com.ronaldo.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldo.curso.domain.Credentials;
import com.ronaldo.curso.dto.AuthenticationDTO;
import com.ronaldo.curso.dto.LoginResponseDTO;
import com.ronaldo.curso.dto.RegisterDTO;
import com.ronaldo.curso.repositories.CredentialsRepository;
import com.ronaldo.curso.services.TokenService;
import com.ronaldo.curso.services.UserService;
import com.ronaldo.curso.services.exception.AuthorizationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CredentialsRepository credentialsRepository;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());

		var auth = this.authenticationManager.authenticate(userNamePassword);
		var token = tokenService.generateToken((Credentials) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
		if (this.credentialsRepository.findByLogin(registerDTO.getLogin()) != null) {
			return ResponseEntity.badRequest().body("email já cadastrado");
		}

		String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());

		Credentials newUser = new Credentials(registerDTO.getLogin(), encryptedPassword, registerDTO.getRole());

		this.credentialsRepository.save(newUser);
		return ResponseEntity.status(201).body("User created");
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		Credentials user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		var token = tokenService.generateToken(user);
		//add no cabecalho header
		response.addHeader("Authorization", token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.ok().body(token);

	}
}