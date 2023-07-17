package com.ronaldo.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.ronaldo.curso.domain.Pagamento;

//@Repository //atualização: não é mais necessário @Repository nas versões atuais
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
