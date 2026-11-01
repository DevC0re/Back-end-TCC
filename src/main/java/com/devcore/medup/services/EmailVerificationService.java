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
import java.util.Locale;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MAX_TENTATIVAS = 5;

    private final TokenVerificacaoRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public EmailVerificationService(
            TokenVerificacaoRepository tokenRepository,
            UserRepository userRepository,
            EmailService emailService
    ) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Transactional(noRollbackFor = ResponseStatusException.class)
    public void confirmar(ConfirmarEmailDTO dto) {

        String email = dto.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        TokenVerificacao registro = tokenRepository
                .findByUsuario_EmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Código inválido ou expirado."
                ));

        if (!registro.getDataExpiracao().isAfter(LocalDateTime.now())) {
            tokenRepository.delete(registro);

            throw new ResponseStatusException(
                    HttpStatus.GONE,
                    "Código expirado. Solicite outro."
            );
        }

        if (registro.getTentativasInvalidas() >= MAX_TENTATIVAS) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas tentativas. Solicite outro código."
            );
        }

        String hashInformado = hashCodigo(dto.getCodigo());

        boolean codigoCorreto = MessageDigest.isEqual(
                hashInformado.getBytes(StandardCharsets.UTF_8),
                registro.getToken().getBytes(StandardCharsets.UTF_8)
        );

        if (!codigoCorreto) {

            registro.incrementarTentativasInvalidas();

            throw new ResponseStatusException(
                    registro.getTentativasInvalidas() >= MAX_TENTATIVAS
                            ? HttpStatus.TOO_MANY_REQUESTS
                            : HttpStatus.BAD_REQUEST,
                    "Código inválido."
            );
        }

        UserEntity usuario = registro.getUsuario();

        usuario.setActive(true);
        userRepository.save(usuario);

        tokenRepository.delete(registro);
    }

    @Transactional
    public void gerarEEnviarCodigo(UserEntity usuario) {

        tokenRepository.deleteByUsuario_Id(usuario.getId());
        tokenRepository.flush();

        String codigo = String.format(
                Locale.ROOT,
                "%06d",
                RANDOM.nextInt(1_000_000)
        );

        TokenVerificacao registro = new TokenVerificacao(
                hashCodigo(codigo),
                LocalDateTime.now(),
                usuario
        );

        tokenRepository.saveAndFlush(registro);

        emailService.enviarCodigoConfirmacao(
                usuario.getEmail(),
                codigo
        );
    }

    @Transactional
    public void reenviar(String emailInformado) {

        String email = emailInformado
                .trim()
                .toLowerCase(Locale.ROOT);

        UserEntity usuario = userRepository
                .findByEmailIgnoreCase(email)
                .orElse(null);

        if (usuario == null || usuario.isActive()) {
            return;
        }

        TokenVerificacao existente = tokenRepository
                .findByUsuario_EmailIgnoreCase(email)
                .orElse(null);

        if (existente != null &&
                existente.getDataCriacao().isAfter(
                        LocalDateTime.now().minusSeconds(60)
                )) {
            return;
        }

        gerarEEnviarCodigo(usuario);
    }

    @Transactional
    public void removerTokensDoUsuario(UUID id) {
        tokenRepository.deleteByUsuario_Id(id);
    }

    private String hashCodigo(String codigo) {

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            return HexFormat.of().formatHex(
                    digest.digest(
                            codigo.getBytes(StandardCharsets.UTF_8)
                    )
            );

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-256 não disponível.", e
            );
        }
    }
}