package com.devcore.medup.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devcore.medup.dtos.reponse.ConvenioResponseDTO;
import com.devcore.medup.dtos.request.ConvenioRequestDTO;
import com.devcore.medup.services.ConvenioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/convenio")
@RequiredArgsConstructor
public class ConvenioController {

    private final ConvenioService convenioService;

    @PostMapping
    public ResponseEntity<ConvenioResponseDTO> saveConvenio(@RequestBody @Valid ConvenioRequestDTO request) {
        ConvenioResponseDTO response = convenioService.saveConvenio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConvenioResponseDTO> getBy(@PathVariable UUID id) {
        ConvenioResponseDTO response = convenioService.searchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConvenioResponseDTO>> getAll() {
        List<ConvenioResponseDTO> list = convenioService.listAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConvenioResponseDTO> update(@PathVariable UUID id,
                                                      @RequestBody @Valid ConvenioRequestDTO request) {
        ConvenioResponseDTO response = convenioService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        convenioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}