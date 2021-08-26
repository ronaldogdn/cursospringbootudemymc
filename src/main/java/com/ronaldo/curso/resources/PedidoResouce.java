package com.ronaldo.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldo.curso.domain.Pedido;
import com.ronaldo.curso.services.PedidoService;

/**
 * Classe para fazer o Controller da api
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResouce {
	/**
	 * Lembrar de sempre instanciar objetos Service
	 */
	@Autowired
	private PedidoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido categoria = service.find(id);
		
		return ResponseEntity.ok().body(categoria);
	}
}
