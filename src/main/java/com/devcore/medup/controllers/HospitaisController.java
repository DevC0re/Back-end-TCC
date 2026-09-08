package com.devcore.medup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devcore.medup.dtos.HospitaisRqsDto;
import com.devcore.medup.dtos.HospitaisRspDto;

@RestController
@RequestMapping("/hospitais")
@RequiredArgsConstructor

public class HospitaisController {

    private final HospitaisService service;

    public HospitaisController(HospitaisService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HospitaisRspDto> saveHospitais(@RequestBody HospitaisRqsDto request) {
        HospitaisRspDto response = service.saveHospitais(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitaisRspDto> searchById(@PathVariable UUID id) {
        HospitaisRspDto response = service.searchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HospitaisRspDto>> listAll() {
        List<HospitaisRspDto> response = service.listAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}