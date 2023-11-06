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
import com.ronaldo.curso.domain.enums.Perfil;
import com.ronaldo.curso.dto.AuthenticationDTO;
import com.ronaldo.curso.dto.LoginResponseDTO;
import com.ronaldo.curso.repositories.CredentialsRepository;
import com.ronaldo.curso.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	TokenService tokenService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
		
		var auth = this.authenticationManager.authenticate(userNamePassword);
		var token = tokenService.generateToken((Credentials)auth.getPrincipal());
		//var token = (Credentials)auth.getPrincipal();
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	//public ResponseEntity<Void> register(@Valid @RequestBody Credentials registerDTO){
		public ResponseEntity<Credentials> register(@Valid @RequestBody Credentials registerDTO){	
		if(this.credentialsRepository.findByLogin(registerDTO.getLogin()) != null)
		{
			return ResponseEntity.badRequest().build();
	    }
		//RegisterDTO register = new RegisterDTO(login,password,role); 
		
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
		Credentials newUser = new Credentials(registerDTO.getLogin(), encryptedPassword, Perfil.ADMIN);
		
		
		this.credentialsRepository.save(newUser);		
		//return ResponseEntity.ok().build();
		return ResponseEntity.status(201).body(newUser);
	}
}