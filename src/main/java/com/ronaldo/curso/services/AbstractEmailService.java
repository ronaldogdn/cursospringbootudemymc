package com.ronaldo.curso.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ronaldo.curso.domain.Pedido;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public abstract class AbstractEmailService implements EmailService{

	//@Value("${default.sender}")
	private String sender = "colocar o email padrão ";
	
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired(required = false)
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		//sendEmail é o método da Interface EmailService
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		//vincula o html ao java
		context.setVariable("pedido", obj);
		//caminho do arquivo, por padrão procura em resources/template; não precisa o .html
		return templateEngine.process("email/confirmacaoPedido", context);		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mimeMessage = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mimeMessage);
		}
		catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
		
		mimeMessageHelper.setTo(obj.getCliente().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(obj), true);
		
		return mimeMessage;
	}
}