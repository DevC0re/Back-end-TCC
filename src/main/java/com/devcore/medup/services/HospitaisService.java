package com.devcore.medup.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcore.medup.dtos.HospitaisRsqDto;
import com.devcore.medup.dtos.HospitaisRpsDto;
import com.devcore.medup.entities.HospitaisEntity;
import com.devcore.medup.repositories.HospitaisRepository;

import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class HospitaisService {
    
    private final HospitaisRepository repository;

    public HospitaisRpsDto saveHospitais(HospitaisRsqDto request) {
            Hospitais hospitais = new Hospitais();
            hospitais.setName(request.getName());
            hospitais.setAddress(request.getAddress());
            hospitais.setPhone(request.getPhone());
            hospitais.setCNPJ(request.getCNPJ());
            hospitais.setEmail(request.getEmail());

          repository.save(hospitais);  
        return convertByRpsDto(hospitais); 
    }

    public HospitaisRspDto searchById(UUID id) {

        Hospitais hospitais = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hospital não encontrado com ID: " + id));

        return convertByRpsDto(hospitais);
    }

    public List<HospitaisRspDto> listAll() {
      
        Hospitais hospitais = repository.findAll()
        .stream()
        .map(this::convertByRpsDto)
        .collect(Collectors.toList());  

        return hospitais;
    }


    public void delete(UUID id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Hospital não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    private HospitaisRspDto convertByRpsDto(Hospitais hospitais) {
        HospitaisRspDto response = new HospitaisRspDto();
        response.setId(hospitais.getId());
        response.setName(hospitais.getName());
        response.setAddress(hospitais.getAddress());
        response.setPhone(hospitais.getPhone());
        response.setCNPJ(hospitais.getCNPJ());
        response.setEmail(hospitais.getEmail());
        return response;
    }
    

}