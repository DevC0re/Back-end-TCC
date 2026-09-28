package com.devcore.medup.controllers;

import com.devcore.medup.dtos.EspecialidadeRpsDto;
import com.devcore.medup.dtos.EspecialidadeRqsDto;
import com.devcore.medup.services.EspecialidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {

    private final EspecialidadeService service;

    @PostMapping
    public ResponseEntity<EspecialidadeRpsDto> create(@RequestBody @Valid EspecialidadeRqsDto request) {
        EspecialidadeRpsDto response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeRpsDto>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeRpsDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.searchById(id));
    }
}