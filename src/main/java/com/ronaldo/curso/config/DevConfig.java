package com.ronaldo.curso.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ronaldo.curso.services.DBService;
import com.ronaldo.curso.services.EmailService;
import com.ronaldo.curso.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	/**
	 * tipo de chave => none, create, update
	 * detecta se é none, create, update no arquivo application-dev para o mysql
	 */	
	@Value("$(spring.jpa.hibernate.ddl-auto=update)")
	private String strategy;
	
	@Bean
	boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		if(!"create".equals(strategy)) {
			return false;
		}
		return true;
	}
	@Bean
	EmailService emailService() {
		return new SmtpEmailService();
	}
}