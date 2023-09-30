package com.ronaldo.curso.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ronaldo.curso.domain.Categoria;
import com.ronaldo.curso.dto.CategoriaDTO;
import com.ronaldo.curso.services.CategoriaService;

import jakarta.validation.Valid;

/**
 * Classe para fazer o Controller da api via HTTP
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria categoria = service.find(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		/**
		 * Ao inserir um novo dado é criado uma nova URI com um novo ID
		 * fromCurrentRequest => pega a URL
		 * path("/{id}") => acrescenta o ID na URI
		 * buildAndExpand(obj.getId()) => pega o ID do objeto para colocar no path
		 * .toUri() => converte para URI
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO> > findAll() {
		//recebe uma lista de Categorias
		List<Categoria> list = service.findAll();
		//transforma a lista de Categorias em uma lista de CategoriaDTO
		List<CategoriaDTO> listDTO = list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO> > findPage(
												@RequestParam(defaultValue ="0") Integer page,
												@RequestParam(defaultValue ="24") Integer linesPerPage, 
												@RequestParam(defaultValue ="nome") String orderBy, 
												@RequestParam(defaultValue ="ASC") String direction){
		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
		//Page já está no java 8
		Page<CategoriaDTO> listDTO = list.map(x -> new CategoriaDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
}