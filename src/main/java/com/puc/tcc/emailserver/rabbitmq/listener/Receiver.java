package com.puc.tcc.emailserver.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puc.tcc.emailserver.component.EmailSenderComponent;
import com.puc.tcc.emailserver.dto.Email;
import com.puc.tcc.emailserver.rabbitmq.config.RabbitMQConfig;

@Service
public class Receiver {
	
	EmailSenderComponent emailSenderComponent;
	
	@Autowired
	public Receiver(EmailSenderComponent emailSenderComponent) {
		this.emailSenderComponent = emailSenderComponent;
	}
	
	@RabbitListener(queues = RabbitMQConfig.ROUTING_EMAIL)
	public void receiveMessage(Email email) {
		System.out.println("Received <Email>");
		StringBuilder sb = new StringBuilder();
		sb.append("\n(Nome: " + email.getNomeDoUsuario() + ")");
		sb.append("\n(Destinatario: " + email.getEmailDestinatario() + ")");
		sb.append("\n(Tipo De Email: " + email.getTipoDeEmail().toString() + ")");
		sb.append("\n");
		
		emailSenderComponent.createMail(email);
	}
	
}