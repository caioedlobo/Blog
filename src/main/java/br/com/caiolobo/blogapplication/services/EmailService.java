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

    public void sendWelcomeMessage(String to, String accountFirstName){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("blogcaioapi@gmail.com");
        message.setTo(to);
        message.setSubject("Bem-vindo ao nosso site!");
        message.setText(
                "Olá " + accountFirstName + ",\n\n" +
                    "Bem-vindo(a) ao meu blog! Ele foi criado com o intuito de praticar o desenvolvimento de APIs com Spring Boot. Não hesite em me contatar se precisar de alguma ajuda, tiver alguma pergunta ou quiser dar um feedback. Obrigado por se cadastrar!\n\n" +
                        "Atenciosamente,\n\n" +
                            "Caio"
        );
        this.emailSender.send(message);
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
