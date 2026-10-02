package com.devcore.medup.services;

import com.devcore.medup.dtos.reponse.UserResponseDTO;
import com.devcore.medup.dtos.request.UserRequestDTO;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.enums.Roles;
import com.devcore.medup.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<UserEntity> listarTodosUsuarios() {
        return userRepository.findAll();
    }

    public UserEntity buscarPorId(UUID id) {
        return userRepository.findById(id).get();
    }

    public void atualizarUsuario(UUID id, UserRequestDTO dto) {
        UserEntity user = buscarPorId(id);
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        userRepository.save(user);
    }

    public void deletarUsuario(UUID id) {
        userRepository.deleteById(id);
    }

}
