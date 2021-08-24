package com.ronaldo.curso;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ronaldo.curso.domain.Categoria;
import com.ronaldo.curso.domain.Cidade;
import com.ronaldo.curso.domain.Cliente;
import com.ronaldo.curso.domain.Endereco;
import com.ronaldo.curso.domain.Estado;
import com.ronaldo.curso.domain.Produto;
import com.ronaldo.curso.domain.enums.TipoCliente;
import com.ronaldo.curso.repositories.CategoriaRepository;
import com.ronaldo.curso.repositories.CidadeRepository;
import com.ronaldo.curso.repositories.ClienteRepository;
import com.ronaldo.curso.repositories.EnderecoRepository;
import com.ronaldo.curso.repositories.EstadoRepository;
import com.ronaldo.curso.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoSpringBootUdemyApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringBootUdemyApplication.class, args);
	}
	/**
	 * Método para inserir automáticamente no h2 em memória ram
	 */
	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"computador",2000.00);
		Produto p2 = new Produto(null,"impressora",800.00);
		Produto p3 = new Produto(null,"mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria da Silva","maria@gmail.com","123456712345",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("328812345","981144792"));
		
		Endereco e1 = new Endereco(null,"Rua A","300","Apto 303","jardim","12345678941",cli1,c1);

		Endereco e2 = new Endereco(null,"Rua B","100","Sala 803","Centro","99345678941",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
	}

}
