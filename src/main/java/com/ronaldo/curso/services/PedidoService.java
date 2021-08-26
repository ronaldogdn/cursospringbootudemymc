package com.ronaldo.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronaldo.curso.domain.Pedido;
import com.ronaldo.curso.repositories.PedidoRepository;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		/**
		 * Atualização do JPA para o java 8+
		 * O Optiona retornal o Objeto ou nulo que deve ser tratado
		 */
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
