package com.ronaldo.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronaldo.curso.domain.Cidade;
import com.ronaldo.curso.domain.Cliente;
import com.ronaldo.curso.domain.Endereco;
import com.ronaldo.curso.domain.enums.TipoCliente;
import com.ronaldo.curso.dto.ClienteDTO;
import com.ronaldo.curso.dto.ClienteNewDTO;
import com.ronaldo.curso.repositories.ClienteRepository;
import com.ronaldo.curso.repositories.EnderecoRepository;
import com.ronaldo.curso.services.exception.DataIntegrityException;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		//se não tem ID no objeto o JPA faz uma inserção nova
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityException("não é possível excluir porque há pedidos relacionados");
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
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), 
									objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateDate(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
