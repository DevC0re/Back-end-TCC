package com.devcore.medup.services;

import com.devcore.medup.dtos.request.ConfirmarEmailDTO;
import com.devcore.medup.entities.TokenVerificacao;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.repositories.TokenVerificacaoRepository;
import com.devcore.medup.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Service
public class EmailVerificationService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final TokenVerificacaoRepository tokenRepository;
    private final UserRepository userRepository;

    public EmailVerificationService(TokenVerificacaoRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * Confere o código de seis dígitos. O token salvo no banco é um hash SHA-256.
     */
    @Transactional
    public void confirmar(ConfirmarEmailDTO dto) {
        TokenVerificacao registro = tokenRepository.findByUsuario_Email(dto.getEmail().trim())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Código inválido ou expirado."));

        if (registro.getDataExpiracao().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(registro);
            throw new ResponseStatusException(HttpStatus.GONE, "Código expirado. Solicite um novo código.");
        }

        String hashInformado = hashCodigo(dto.getCodigo());
        if (!MessageDigest.isEqual(
                hashInformado.getBytes(StandardCharsets.UTF_8),
                registro.getToken().getBytes(StandardCharsets.UTF_8))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código inválido ou expirado.");
        }

        UserEntity usuario = registro.getUsuario();
        usuario.setActive(true);
        userRepository.save(usuario);
        tokenRepository.deleteByUsuario_Id(usuario.getId());
    }

    /**
     * Gera um código aleatório seguro. Chamado durante o cadastro.
     * Remove tokens anteriores, grava somente o hash e envia o código original por e-mail.
     */
    @Transactional
    public void gerarEEnviarCodigo(UserEntity usuario, EmailService emailService) {
        tokenRepository.deleteByUsuario_Id(usuario.getId());

        String codigo = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));
        TokenVerificacao registro = new TokenVerificacao(
                hashCodigo(codigo), LocalDateTime.now().plusMinutes(10), usuario);
        tokenRepository.save(registro);

        emailService.enviarCodigoConfirmacao(usuario.getEmail(), codigo);
    }

    private String hashCodigo(String codigo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(codigo.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não está disponível nesta JVM.", e);
        }
    }
}
