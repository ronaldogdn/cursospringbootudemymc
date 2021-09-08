package com.ronaldo.curso.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ronaldo.curso.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	//tipo de chave => none, create, update
	@Value("$(spring.jpa.hibernate.ddl-auto)")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		if(!"create".equals(strategy)) {
			return false;
		}
		return true;
	}
}
