package com.ronaldo.curso.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ronaldo.curso.services.DBService;
/**
 * Arquivo responsável pelo profile de Test
 * Os dados automáticos estão na Classe DBService
 */
import com.ronaldo.curso.services.EmailService;
import com.ronaldo.curso.services.MockEmailService;
@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

    @Bean
    boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();		
		return true;
	}
    
    @Bean
    EmailService emailService() {
    	return new MockEmailService();
    }
}
