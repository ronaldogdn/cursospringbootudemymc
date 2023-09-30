package com.ronaldo.curso.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ronaldo.curso.domain.ItemPedido;
import com.ronaldo.curso.domain.PagamentoComBoleto;
import com.ronaldo.curso.domain.Pedido;
import com.ronaldo.curso.domain.enums.EstadoPagamento;
import com.ronaldo.curso.repositories.ClienteRepository;
import com.ronaldo.curso.repositories.ItemPedidoRepository;
import com.ronaldo.curso.repositories.PagamentoRepository;
import com.ronaldo.curso.repositories.PedidoRepository;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService ;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		/**
		 * Atualização do JPA para o java 8+
		 * O Optional retorna o Objeto ou nulo que deve ser tratado
		 */
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findById(obj.getCliente().getId()).orElse(null));
		//todo pedido novo tem o pagamneot como pendente
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		/**
		 * No mundo real teria um gateway de pagamento para o boleto
		 */
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			//Pagamento é a uma classe abstract por isso a conversão de tipo
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			//a data de pagamento é simulada com uma semana depois de gerar o pedido
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
}