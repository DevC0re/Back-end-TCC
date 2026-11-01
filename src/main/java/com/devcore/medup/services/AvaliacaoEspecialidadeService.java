package com.devcore.medup.services;

import com.devcore.medup.dtos.AvaliacaoEspecialidadeRpsDto;
import com.devcore.medup.dtos.AvaliacaoEspecialidadeRqsDto;
import com.devcore.medup.entities.AvaliacaoEspecialidadeEntity;
import com.devcore.medup.entities.EspecialidadeEntity;
import com.devcore.medup.entities.HospitaisEntity;
import com.devcore.medup.repositories.AvaliacaoEspecialidadeRepository;
import com.devcore.medup.repositories.EspecialidadeRepository;
import com.devcore.medup.repositories.HospitaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AvaliacaoEspecialidadeService {

    private final AvaliacaoEspecialidadeRepository repository;
    private final HospitaisRepository hospitaisRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public AvaliacaoEspecialidadeRpsDto save(AvaliacaoEspecialidadeRqsDto request) {
        HospitaisEntity hospital = hospitaisRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital não encontrado com ID: " + request.getHospitalId()));

        EspecialidadeEntity especialidade = especialidadeRepository.findById(request.getEspecialidadeId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + request.getEspecialidadeId()));

        AvaliacaoEspecialidadeEntity entity = new AvaliacaoEspecialidadeEntity();
        entity.setNota(request.getNota());
        entity.setObservacao(request.getObservacao());
        entity.setDataAvaliacao(LocalDate.now());
        entity.setHospital(hospital);
        entity.setEspecialidade(especialidade);

        AvaliacaoEspecialidadeEntity saved = repository.save(entity);
        return convertToDto(saved);
    }

    public List<AvaliacaoEspecialidadeRpsDto> listByHospital(UUID hospitalId) {
        return repository.findByHospitalId(hospitalId).stream()
                .map(this::convertToDto)
                .toList();
    }

    private AvaliacaoEspecialidadeRpsDto convertToDto(AvaliacaoEspecialidadeEntity entity) {
        return new AvaliacaoEspecialidadeRpsDto(
                entity.getId(),
                entity.getNota(),
                entity.getObservacao(),
                entity.getDataAvaliacao(),
                entity.getHospital().getName(),
                entity.getEspecialidade().getNome()
        );
    }
}