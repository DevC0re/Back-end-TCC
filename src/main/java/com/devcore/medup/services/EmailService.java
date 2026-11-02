package com.devcore.medup.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final String remetente;

    public EmailService(JavaMailSender mailSender,
                        @Value("${spring.mail.username:}") String remetente) {
        this.mailSender = mailSender;
        this.remetente = remetente;
    }

    // Síncrono: falha SMTP provoca erro e impede cadastro silenciosamente incompleto.
    public void enviarCodigoConfirmacao(String emailDestino, String codigo) {
        if (remetente.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "O serviço de e-mail não está configurado.");
        }

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(emailDestino);
        mensagem.setSubject("Código de confirmação - MedUp");
        mensagem.setText("Olá!\n\nSeu código de confirmação do MedUp é: " + codigo
                + "\n\nO código expira em 10 minutos e só pode ser usado uma vez."
                + "\n\nSe você não criou esta conta, ignore esta mensagem.");

        try {
            mailSender.send(mensagem);
        } catch (MailException e) {
            // Não registrar o código em logs.
            log.error("Falha de envio de confirmação por e-mail.", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Não foi possível enviar o código de confirmação. Tente novamente.", e);
        }
    }
}
