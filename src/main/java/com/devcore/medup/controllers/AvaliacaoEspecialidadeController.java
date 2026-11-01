package com.devcore.medup.controllers;

import com.devcore.medup.dtos.AvaliacaoEspecialidadeRpsDto;
import com.devcore.medup.dtos.AvaliacaoEspecialidadeRqsDto;
import com.devcore.medup.services.AvaliacaoEspecialidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/avaliacoes-especialidades")
@RequiredArgsConstructor
public class AvaliacaoEspecialidadeController {

    private final AvaliacaoEspecialidadeService service;

    @PostMapping
    public ResponseEntity<AvaliacaoEspecialidadeRpsDto> create(@RequestBody @Valid AvaliacaoEspecialidadeRqsDto request) {
        AvaliacaoEspecialidadeRpsDto response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<AvaliacaoEspecialidadeRpsDto>> listByHospital(@PathVariable UUID hospitalId) {
        return ResponseEntity.ok(service.listByHospital(hospitalId));
    }
}