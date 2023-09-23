package com.ronaldo.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldo.curso.domain.Produto;
import com.ronaldo.curso.dto.ProdutoDTO;
import com.ronaldo.curso.resources.utils.URL;
import com.ronaldo.curso.services.ProdutoService;

/**
 * Classe para fazer o Controller da api
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {
	/**
	 * Lembrar de sempre instanciar objetos Service
	 */
	@Autowired
	private ProdutoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	/**
	 * 
	 * @param nome: nome ou parte do nome a ser procuradas
	 * @param categorias: lista de IDs das categorias que serão buscadas
	 * @param linesPerPage: quantidade linhas por páginas
	 * @param orderBy ordenação da página: padrão é ordenada por nome
	 * @param direction ASC ou DESC
	 * @return retorna uma lista paginada
	 * exemplo de busca: {{host}}/produtos/page?nome=or&categorias=1,4
	 */
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ProdutoDTO> > findPage(
			@RequestParam(value = "nome", defaultValue ="") String nome,
			@RequestParam(value = "categorias", defaultValue="") String categorias, 
			@RequestParam(value = "page", defaultValue ="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue ="24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue ="nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue ="ASC") String direction){
		//faz o decoder do nome
		String nomeDecoded = URL.decodeParam(nome);
		//separa os IDs da categoria por virgula e cria a lista de inteiros
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded,ids,page,linesPerPage,orderBy,direction);
		//Page já está no java 8
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
