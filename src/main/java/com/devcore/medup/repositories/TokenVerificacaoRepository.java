package com.devcore.medup.repositories;

import com.devcore.medup.entities.TokenVerificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TokenVerificacaoRepository extends JpaRepository<TokenVerificacao, UUID> {
    Optional<TokenVerificacao> findByToken(String tokenHash);
    Optional<TokenVerificacao> findByUsuario_Email(String email);
    void deleteByUsuario_Id(UUID usuarioId);
}
