package com.ronaldo.curso.resources;

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

/**
 * Classe para fazer o Controller da api
 * @author ronaldo
 */
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResouce {
	/**
	 * Lembrar de sempre instanciar objetos Service
	 */
	@Autowired
	private CategoriaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria categoria = service.find(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		/**
		 * Ao inserir um novo dado é criado uma nova URI com um novo ID
		 * fromCurrentRequest => pega a URI
		 * path("/{id}") => acrescenta o ID na URI
		 * buildAndExpand(obj.getId()) => pega o ID do objeto para colocar no path
		 * .toUri() => converte para URI
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
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
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO> > findPage(
												@RequestParam(value = "page", defaultValue ="0") Integer page,
												@RequestParam(value = "linesPerPage", defaultValue ="24") Integer linesPerPage, 
												@RequestParam(value = "orderBy", defaultValue ="nome") String orderBy, 
												@RequestParam(value = "direction", defaultValue ="ASC") String direction){
		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
		//Page já está no java 8
		Page<CategoriaDTO> listDTO = list.map(x -> new CategoriaDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
	
	
}
