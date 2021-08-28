package com.ronaldo.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronaldo.curso.domain.Cliente;
import com.ronaldo.curso.dto.ClienteDTO;
import com.ronaldo.curso.repositories.ClienteRepository;
import com.ronaldo.curso.services.exception.DataIntegrityException;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	public Cliente update(Cliente obj) {
		//busca o cliente antigo para saber se o cliente existe
		Cliente newObj = find(obj.getId());
		//faz a atualização com os novos dados
		updateDate(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
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
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
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
		} catch (Exception e) {
			throw new DataIntegrityException("não é possível mostrar as categorias");
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}

	private void updateDate(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
