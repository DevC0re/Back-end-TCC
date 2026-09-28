package com.devcore.medup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devcore.medup.dtos.HospitaisRsqDto;
import com.devcore.medup.dtos.HospitaisRpsDto;
import com.devcore.medup.services.HospitaisService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hospitais")
@RequiredArgsConstructor
public class HospitaisController {

    private final HospitaisService service;

    @PostMapping
    public ResponseEntity<HospitaisRpsDto> saveHospitais(@RequestBody HospitaisRsqDto request) {
        HospitaisRpsDto response = service.saveHospitais(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitaisRpsDto> searchById(@PathVariable UUID id) {
        HospitaisRpsDto response = service.searchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HospitaisRpsDto>> listAll() {
        List<HospitaisRpsDto> response = service.listAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}