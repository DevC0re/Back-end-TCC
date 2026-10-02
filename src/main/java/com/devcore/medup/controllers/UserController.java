package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.UserRequestDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> listarTarefas() {
        return ResponseEntity.ok(userService.listarTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
         return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTarefa(@PathVariable UUID id, @Valid @RequestBody UserRequestDTO dto) {
        userService.atualizarUsuario(id, dto);
        return ResponseEntity.ok("Tarefa atualizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTarefa(@PathVariable UUID id) {
        userService.deletarUsuario(id);
        return ResponseEntity.ok("Tarefa deletada com sucesso!");
    }

}

