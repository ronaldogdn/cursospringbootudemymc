package com.ronaldo.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronaldo.curso.domain.Categoria;
import com.ronaldo.curso.domain.Produto;
import com.ronaldo.curso.repositories.CategoriaRepository;
import com.ronaldo.curso.repositories.ProdutoRepository;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		/**
		 * Atualização do JPA para o java 8+
		 * O Optiona retornal o Objeto ou nulo que deve ser tratado
		 */
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	/**
	 * 
	 * @param nome: nome ou parte do nome a ser procurado
	 * @param ids: lista de IDs para procurar nas categorias
	 * @param page: numero das paginas
	 * @param linesPerPage: quantidade linhas por páginas
	 * @param orderBy ordenação da página: padrão é ordenada por nome
	 * @param direction ASC ou DESC
	 * @return retorna uma lista de Páginas
	 */
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistincByNomeContainingAndCategoriasIn(nome, categorias,pageRequest);
	}
}