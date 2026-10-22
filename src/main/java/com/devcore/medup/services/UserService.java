package com.devcore.medup.services;

import com.devcore.medup.dtos.request.UserRequestDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.enums.Roles;
import com.devcore.medup.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final EmailVerificationService emailVerificationService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       EmailService emailService, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.emailVerificationService = emailVerificationService;
    }

    @Transactional
    public String createUser(UserRequestDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe usuário com este e-mail.");
        }

        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setEmail(email);
        user.setCpf(dto.getCpf());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Roles.USER);
        user.setActive(false);
        user = userRepository.save(user);

        emailVerificationService.gerarEEnviarCodigo(user, emailService);
        return "Usuário cadastrado. Confira seu e-mail e informe o código de 6 dígitos para ativar a conta.";
    }
}
