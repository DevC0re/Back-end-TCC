package com.devcore.medup.controllers;

import com.devcore.medup.dtos.reponse.EnderecoResponseDTO;
import com.devcore.medup.dtos.request.EnderecoRequestDTO;
import com.devcore.medup.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuario/me/enderecos")
public class EnderecoController {
    private final EnderecoService service;

    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> listar(Authentication auth) {
        return ResponseEntity.ok(service.listarMeus(auth));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscar(@PathVariable UUID id, Authentication auth) {
        return ResponseEntity.ok(service.buscarMeu(id, auth));
    }

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> criar(@Valid @RequestBody EnderecoRequestDTO dto,
                                                       Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarMeu(dto, auth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizar(@PathVariable UUID id,
                                                           @Valid @RequestBody EnderecoRequestDTO dto,
                                                           Authentication auth) {
        return ResponseEntity.ok(service.atualizarMeu(id, dto, auth));
    }

    @PatchMapping("/{id}/principal")
    public ResponseEntity<EnderecoResponseDTO> principal(@PathVariable UUID id, Authentication auth) {
        return ResponseEntity.ok(service.definirPrincipal(id, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id, Authentication auth) {
        service.excluirMeu(id, auth);
        return ResponseEntity.noContent().build();
    }
}
