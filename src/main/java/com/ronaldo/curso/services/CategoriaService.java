package com.ronaldo.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronaldo.curso.domain.Categoria;
import com.ronaldo.curso.repositories.CategoriaRepository;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		/**
		 * Atualização do JPA para o java 8+
		 * O Optiona retornal o Objeto ou nulo que deve ser tratado
		 */
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}
