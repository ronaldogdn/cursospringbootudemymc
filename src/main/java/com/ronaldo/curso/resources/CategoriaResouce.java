package com.ronaldo.curso.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe para fazer o Controller da api
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "categorias")
public class CategoriaResouce {

	@GetMapping
	public String listar() {
		return "Rest está funcionando";
	}
}
