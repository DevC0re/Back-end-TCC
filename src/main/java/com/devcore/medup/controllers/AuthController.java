package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.LoginDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.services.JwtService;
import com.devcore.medup.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserService userService;

    public AuthController(JwtService jwtService,
                          AuthenticationManager authManager,
                          UserService userService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                )
        );
        UserEntity user = userService.buscarPorEmail(loginDTO.getEmail());
        return jwtService.gerarToken(user.getEmail(), user.getId());
    }
}
