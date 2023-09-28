package team3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mail;

	public void sendEmail(String toEmail, String subject, String body) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("chisae0123@gmail.com");
//		message.setTo(toEmail);
//		message.setSubject(subject);
//		message.setText(body);
//		mail.send(message);

		try {
			MimeMessage message = mail.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("chisae0123@gmail.com");
			helper.setTo(toEmail);
			helper.setText(body, true);
			mail.send(message);
		} catch (MessagingException ex) {

		}
	}
//	mail.sendEmail(u.getEmail(), "Account Activation",
//			"<a href=" + getBaseUrl() + "/activate/" + getMd5(u.getEmail() + u.getName() + u.getPasswd())
//					+ ">click here to activate your account with " + u.getEmail() + " </a>");
}
