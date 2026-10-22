package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.ConfirmarEmailDTO;
import com.devcore.medup.services.EmailVerificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class EmailVerificationController {
    private final EmailVerificationService verificationService;

    public EmailVerificationController(EmailVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    /** Recebe e compara o código de seis dígitos enviado por e-mail. */
    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmar(@Valid @RequestBody ConfirmarEmailDTO dto) {
        verificationService.confirmar(dto);
        return ResponseEntity.ok("E-mail confirmado. Sua conta está ativa; você já pode entrar.");
    }
}
