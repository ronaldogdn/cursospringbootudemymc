package com.ronaldo.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronaldo.curso.domain.Categoria;
import com.ronaldo.curso.repositories.CategoriaRepository;
import com.ronaldo.curso.services.exception.DataIntegrityException;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		/**
		 * Atualização do JPA para o java 8+
		 * O Optiona retornal o Objeto ou nulo que deve ser tratado
		 */
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		//se não tem ID no objeto o JPA faz uma inserção nova
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("não é possível excluir uma categoria que tem produtos");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	/**
	 * 
	 * @param page Contagem de página: 0,1,2...
	 * @param linesPerPage Quantas linhas por página eu quero
	 * @param orderBy Campo a ser ordenado
	 * @param direction Ordenação ASC ou DESC
	 * @return
	 */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		boolean error = true;
		if(page < 0 || linesPerPage < 0) {
			throw new DataIntegrityException("não é possível mostrar as categorias");
		}
		if(orderBy.equals("id") || orderBy.equals("nome")) {
			error = false;
		}
		
		if(error) {
			throw new DataIntegrityException("não é possível mostrar as categorias");
		}
		try {
			//para spring versão 2 ou superior
			PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction),orderBy);
			return repo.findAll(pageRequest);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("não é possível mostrar as categorias");
		}
	}
}
