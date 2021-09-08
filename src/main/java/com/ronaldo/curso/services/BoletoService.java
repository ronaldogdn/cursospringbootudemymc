package com.ronaldo.curso.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ronaldo.curso.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		/**
		 * Cria um data de pagamento fake
		 * Em situação real teria que trocar por um web service de pagamento
		 * de boleto
		 */
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
