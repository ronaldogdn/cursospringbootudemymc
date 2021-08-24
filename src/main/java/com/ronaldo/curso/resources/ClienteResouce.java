package com.ronaldo.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldo.curso.domain.Cliente;
import com.ronaldo.curso.services.ClienteService;

/**
 * Classe para fazer o Controller da api
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResouce {
	/**
	 * Lembrar de sempre instanciar objetos Service
	 */
	@Autowired
	private ClienteService categoriaService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente categoria = categoriaService.buscar(id);
		
		return ResponseEntity.ok().body(categoria);
	}
}
