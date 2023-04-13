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

    public void sendRecoveryMessage(String to, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("blogcaioapi@gmail.com");
        message.setTo(to);
        message.setSubject("Recuperação de senha");
        message.setText("Olá, segue aqui o link de recuperação de senha: blogcaiolobo.netlify.app/forgot-password/" + token);
        this.emailSender.send(message);
    }

}
