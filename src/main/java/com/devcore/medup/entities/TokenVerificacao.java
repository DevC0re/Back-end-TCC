package com.devcore.medup.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Guarda o hash do código de confirmação, nunca o código em texto puro.
 * O código é temporário e só pode ser usado uma vez.
 */
@Entity
@Table(name = "tokens_verificacao", indexes = {
        @Index(name = "idx_token_verificacao_hash", columnList = "token", unique = true)
})
public class TokenVerificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // SHA-256 hexadecimal possui 64 caracteres.
    @Column(nullable = false, unique = true, length = 64)
    private String token;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity usuario;

    public TokenVerificacao() {}

    public TokenVerificacao(String token, LocalDateTime dataExpiracao, UserEntity usuario) {
        this.token = token;
        this.dataExpiracao = dataExpiracao;
        this.usuario = usuario;
    }

    public UUID getId() { return id; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(LocalDateTime dataExpiracao) { this.dataExpiracao = dataExpiracao; }
    public UserEntity getUsuario() { return usuario; }
    public void setUsuario(UserEntity usuario) { this.usuario = usuario; }
}
