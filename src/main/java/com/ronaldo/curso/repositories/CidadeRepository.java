package com.ronaldo.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.ronaldo.curso.domain.Cidade;

//@Repository //atualização: não é mais necessário @Repository nas versões atuais
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
