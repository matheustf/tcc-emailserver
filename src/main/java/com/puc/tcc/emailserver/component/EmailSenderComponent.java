package com.puc.tcc.emailserver.component;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.puc.tcc.emailserver.dto.Email;
import com.puc.tcc.emailserver.enums.TipoDeEmail;
import com.puc.tcc.emailserver.utils.Util;

@Component
public class EmailSenderComponent {

	private JavaMailSender javaMailSender;

	@Autowired
	public EmailSenderComponent(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public EmailSenderComponent() {
		// TODO Auto-generated constructor stub
	}

	public void createMail(Email email) {
		try {
			
			TipoDeEmail tipoDeEmail = TipoDeEmail.valueOf(email.getTipoDeEmail());
			
			email = tipoDeEmail.execute(email);
			MimeMessage mimeMessage = emailToSimpleMailMessage(email);
			javaMailSender.send(mimeMessage);

		} catch (MessagingException e) {
			System.out.println("Error send mail");
		}
	}

	private MimeMessage emailToSimpleMailMessage(Email email) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(email.getTitulo());
		helper.setTo(email.getEmailDestinatario());
		helper.setFrom("salesstoreofficial@gmail.com");

		String contentFile = createContentEmail(email);

		helper.setText(contentFile, true);

		return message;
	}

	private String createContentEmail(Email email) {
		String contentFile = Util.readEmail(email.getFileName());

		for (Entry<String, String> entry : email.getContentReplace().entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			contentFile = contentFile.replace(key, value);

		}
		return contentFile;
	}

	public static void main(String[] args) throws MessagingException {

		HashMap<String, String> selects = new HashMap<String, String>();
		selects.put("**usuario**", "Matheus Teles");
		selects.put("**status**", "ENTREGUE");

		Email email = Email.builder().emailDestinatario("teles.matheus@hotmail.com").nomeDoUsuario("Matheus Teles")
				.tipoDeEmail(TipoDeEmail.ENTREGA.name()).contentReplace(selects).build();

		TipoDeEmail tipoDeEmail = TipoDeEmail.valueOf(email.getTipoDeEmail());
		email = tipoDeEmail.execute(email);

		MimeMessage emailToSimpleMailMessage = new EmailSenderComponent(new JavaMailSenderImpl())
				.emailToSimpleMailMessage(email);
	}

}
