package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.UserRequestDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/admin")
    public String admin(){
        return "Acesso ADMIN";
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios() {   // só ADMIN (garantido no SecurityConfig)
        return ResponseEntity.ok(userService.listarTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id, Authentication auth) {
        if (!podeAcessar(id, auth)) return negado();
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable UUID id,
                                              @Valid @RequestBody UserRequestDTO dto,
                                              Authentication auth) {
        if (!podeAcessar(id, auth)) return negado();
        userService.atualizarUsuario(id, dto);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable UUID id, Authentication auth) {
        if (!podeAcessar(id, auth)) return negado();
        userService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

    private boolean podeAcessar(UUID id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) return true;
        return userService.buscarPorEmail(auth.getName()).getId().equals(id);
    }

    private ResponseEntity<String> negado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Você só pode acessar a sua própria conta.");
    }
}

