package com.ronaldo.curso.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.ronaldo.curso.domain.Categoria;

import jakarta.validation.constraints.NotEmpty;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * para documentação sobre validação:
	 * https://www.baeldung.com/javax-validation
	 * https://www.baeldung.com/javax-validation-method-constraints
	 * https://www.baeldung.com/spring-boot-bean-validation
	 * https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm 
	**/
	private Integer id;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5,max = 80,message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	
	public CategoriaDTO() {}

	/**
	 * Usa o construtor com argumentos para transforma de Categoria para CategoriaDTO
	 * @param obj recebe um objeto do tipo Categoria
	 * @return um objeto do tipo CategoriaDTO
	 */
	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
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
}