package com.devcore.medup.repositories;

import com.devcore.medup.entities.TokenVerificacao;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface TokenVerificacaoRepository extends JpaRepository<TokenVerificacao, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TokenVerificacao> findByUsuario_EmailIgnoreCase(String email);

    void deleteByUsuario_Id(UUID usuarioId);
}
