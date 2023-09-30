package com.ronaldo.curso.services;

import org.springframework.mail.SimpleMailMessage;

import com.ronaldo.curso.domain.Cliente;
import com.ronaldo.curso.domain.Pedido;

import jakarta.mail.internet.MimeMessage;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
}