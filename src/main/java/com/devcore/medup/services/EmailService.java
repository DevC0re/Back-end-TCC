package com.devcore.medup.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /** Envia o código numérico de confirmação, sem link. */
    @Async
    public void enviarCodigoConfirmacao(String emailDestino, String codigo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(from);
        mensagem.setTo(emailDestino);
        mensagem.setSubject("Código de confirmação - MedUp");
        mensagem.setText("Olá!\n\nSeu código de confirmação do MedUp é: " + codigo
                + "\n\nO código expira em 10 minutos e só pode ser usado uma vez."
                + "\n\nSe você não criou esta conta, ignore esta mensagem.");

        try {
            mailSender.send(mensagem);
        } catch (MailException e) {
            // Registra a falha no servidor sem registrar o código de verificação.
            log.error("Falha ao enviar e-mail de confirmação para o destinatário informado.", e);
        }
    }
}
