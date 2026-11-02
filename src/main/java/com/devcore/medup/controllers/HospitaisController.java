package com.devcore.medup.controllers;

import com.devcore.medup.dtos.reponse.EnderecoResponseDTO;
import com.devcore.medup.dtos.reponse.HospitaisRpsDto;
import com.devcore.medup.dtos.request.EnderecoRequestDTO;
import com.devcore.medup.dtos.request.HospitaisRsqDto;
import com.devcore.medup.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import com.devcore.medup.services.HospitaisService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hospitais")
@RequiredArgsConstructor
public class HospitaisController {

    private final HospitaisService service;
    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<HospitaisRpsDto> saveHospitais(@Valid @RequestBody HospitaisRsqDto request) {
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

    @GetMapping("/{id}/endereco")
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(@PathVariable UUID id) {
        return ResponseEntity.ok(enderecoService.buscarHospital(id));
    }

    @PutMapping("/{id}/endereco")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable UUID id,
            @Valid @RequestBody EnderecoRequestDTO endereco, Authentication auth) {
        if (auth == null || auth.getAuthorities().stream()
                .noneMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem alterar o endereço do hospital.");
        }
        return ResponseEntity.ok(enderecoService.salvarHospital(id, endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}