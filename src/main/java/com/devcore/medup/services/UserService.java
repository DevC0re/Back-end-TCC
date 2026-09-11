package com.devcore.medup.services;

import com.devcore.medup.dtos.reponse.UserResponseDTO;
import com.devcore.medup.dtos.request.UserRequestDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.enums.Roles;
import com.devcore.medup.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String createUser(UserRequestDTO dto) {

        UserEntity user = new UserEntity();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Roles.USER);
        userRepository.save(user);

        return "Usuário criado com Sucesso";
    }
}
