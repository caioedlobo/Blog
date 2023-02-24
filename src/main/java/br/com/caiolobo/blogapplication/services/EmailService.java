package br.com.caiolobo.blogapplication.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMessage(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("blogcaioapi@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }

}
