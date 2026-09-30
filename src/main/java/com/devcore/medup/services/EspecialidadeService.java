package com.devcore.medup.services;

import com.devcore.medup.dtos.EspecialidadeRpsDto;
import com.devcore.medup.dtos.EspecialidadeRqsDto;
import com.devcore.medup.entities.EspecialidadeEntity;
import com.devcore.medup.repositories.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeRpsDto save(EspecialidadeRqsDto request) {
        EspecialidadeEntity entity = new EspecialidadeEntity();
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());

        EspecialidadeEntity saved = repository.save(entity);
        return convertToDto(saved);
    }

    public List<EspecialidadeRpsDto> listAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public EspecialidadeRpsDto searchById(UUID id) {
        EspecialidadeEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + id));
        return convertToDto(entity);
    }

    private EspecialidadeRpsDto convertToDto(EspecialidadeEntity entity) {
        return new EspecialidadeRpsDto(entity.getId(), entity.getNome(), entity.getDescricao());
    }
}