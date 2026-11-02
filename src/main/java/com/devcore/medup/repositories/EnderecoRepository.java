package com.devcore.medup.repositories;

import com.devcore.medup.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, UUID> {
    List<EnderecoEntity> findByUsuario_IdOrderByPrincipalDesc(UUID usuarioId);

    Optional<EnderecoEntity> findByIdAndUsuario_Id(UUID id, UUID usuarioId);

    Optional<EnderecoEntity> findByHospital_Id(UUID hospitalId);
}
