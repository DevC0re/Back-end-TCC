package com.devcore.medup.controllers;

import com.devcore.medup.dtos.request.LoginDTO;
import com.devcore.medup.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                )
        );
        return jwtService.gerarToken(loginDTO.getEmail());
    }
}
