package com.devcore.medup.repositories;

import com.devcore.medup.entities.AvaliacaoEspecialidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoEspecialidadeRepository extends JpaRepository<AvaliacaoEspecialidadeEntity, UUID> {
    List<AvaliacaoEspecialidadeEntity> findByHospitalId(UUID hospitalId);
    List<AvaliacaoEspecialidadeEntity> findByEspecialidadeId(UUID especialidadeId);
}