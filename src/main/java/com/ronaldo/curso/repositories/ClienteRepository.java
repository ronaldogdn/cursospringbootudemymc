package com.ronaldo.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ronaldo.curso.domain.Cliente;

//@Repository //atualização: não é mais necessário @Repository nas versões atuais
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	//strping data doc para ver as posibilidades
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
