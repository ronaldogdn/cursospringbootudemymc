package com.ronaldo.curso.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.ronaldo.curso.domain.Cliente;
import com.ronaldo.curso.services.validation.ClienteUpdate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5,max = 120,message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email
	private String email;
	
	public ClienteDTO() {}
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
