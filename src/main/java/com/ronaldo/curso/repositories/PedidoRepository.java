package com.ronaldo.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ronaldo.curso.domain.Pedido;

//@Repository => nas versões 3.1 ou superiro não é mais necessário
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
