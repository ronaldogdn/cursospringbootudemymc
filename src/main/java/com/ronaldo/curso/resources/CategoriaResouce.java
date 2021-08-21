package com.ronaldo.curso.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldo.curso.domain.Categoria;

/**
 * Classe para fazer o Controller da api
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResouce {

	@GetMapping
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria(1,"informática");
		Categoria cat2 = new Categoria(1,"informática");

		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
}
