package com.devcore.medup.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcore.medup.dtos.reponse.ConvenioResponseDTO;
import com.devcore.medup.dtos.request.ConvenioRequestDTO;
import com.devcore.medup.entities.ConvenioEntity;
import com.devcore.medup.repositories.ConvenioRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ConvenioService {

    private final ConvenioRepository repository;

    public ConvenioResponseDTO saveConvenio(ConvenioRequestDTO request) {
        ConvenioEntity convenio = new ConvenioEntity();
        convenio.setName(request.getName());
        convenio.setHospitaisAceitos(
                request.getHospitaisAceitos() != null ? request.getHospitaisAceitos() : new ArrayList<>());
        convenio.setCnpj(request.getCnpj());

        ConvenioEntity save = repository.save(convenio);
        return convertByResponseDto(save);
    }

    public ConvenioResponseDTO searchById(UUID id) {
        ConvenioEntity convenio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não encontrado com ID: " + id));

        return convertByResponseDto(convenio);
    }

    public List<ConvenioResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(this::convertByResponseDto)
                .toList();
    }

    public ConvenioResponseDTO update(UUID id, ConvenioRequestDTO request) {
        ConvenioEntity convenio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não encontrado com ID: " + id));

        convenio.setName(request.getName());
        convenio.setHospitaisAceitos(
                request.getHospitaisAceitos() != null ? request.getHospitaisAceitos() : new ArrayList<>());
        convenio.setCnpj(request.getCnpj());

        ConvenioEntity save = repository.save(convenio);
        return convertByResponseDto(save);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Convênio não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    private ConvenioResponseDTO convertByResponseDto(ConvenioEntity convenio) {
        ConvenioResponseDTO response = new ConvenioResponseDTO();
        response.setName(convenio.getName());
        response.setHospitaisAceitos(convenio.getHospitaisAceitos());
        response.setCnpj(convenio.getCnpj());
        return response;
    }
}