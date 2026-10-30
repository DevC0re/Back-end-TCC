package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.UserRequestDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
