package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.ConfirmarEmailDTO;
import com.devcore.medup.dtos.request.ReenviarCodigoDTO;
import com.devcore.medup.services.EmailVerificationService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class EmailVerificationController {

    private final EmailVerificationService verificationService;

    public EmailVerificationController(
            EmailVerificationService verificationService
    ) {
        this.verificationService = verificationService;
    }

    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmar(
            @Valid @RequestBody ConfirmarEmailDTO dto
    ) {
        verificationService.confirmar(dto);

        return ResponseEntity.ok(
                "E-mail confirmado. Sua conta está ativa."
        );
    }

    @PostMapping("/reenviar")
    public ResponseEntity<String> reenviar(
            @Valid @RequestBody ReenviarCodigoDTO dto
    ) {
        verificationService.reenviar(dto.getEmail());

        return ResponseEntity.ok(
                "Se a conta estiver pendente, um novo código será enviado quando permitido."
        );
    }
}