package com.ronaldo.curso.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ronaldo.curso.domain.Categoria;
import com.ronaldo.curso.domain.Produto;

//@Repository //atualização: não é mais necessário @Repository nas versões atuais
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	/**
	 * Pode ser usada a QUERY diretamente ou find do JPA
	 */
//	@Transactional(readOnly=true)
//	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
//	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	@Transactional(readOnly=true)
	Page<Produto> findDistincByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias,Pageable pageRequest);
}