package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.dto.EmailDTO;
import com.devsuperior.demo.services.exceptions.EmailException;

import java.util.List;

@Service
public class EmailService {

	@Value("${spring.mail.username}")
	private String emailFrom;

	@Autowired
	private JavaMailSender emailSender;

	public void sendEmail(EmailDTO obj) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailFrom);
			List<String> toList = obj.getTo();
			String[] toArr = new String[toList.size()];
			toArr = toList.toArray(toArr);
			message.setTo(toArr);
			message.setSubject(obj.getSubject());
			message.setText(obj.getBody());
			emailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
			throw new EmailException("Failed to send email");
		}
	}

}
