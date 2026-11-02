package com.devcore.medup.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens_verificacao")
public class TokenVerificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private int tentativasInvalidas;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UserEntity usuario;

    protected TokenVerificacao() { }

    public TokenVerificacao(String token, LocalDateTime agora, UserEntity usuario) {
        this.token = token;
        this.dataCriacao = agora;
        this.dataExpiracao = agora.plusMinutes(10);
        this.usuario = usuario;
    }

    public UUID getId() { return id; }
    public String getToken() { return token; }
    public LocalDateTime getDataExpiracao() { return dataExpiracao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public int getTentativasInvalidas() { return tentativasInvalidas; }
    public void incrementarTentativasInvalidas() { tentativasInvalidas++; }
    public UserEntity getUsuario() { return usuario; }
}
