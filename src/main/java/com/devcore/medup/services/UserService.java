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

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationService = emailVerificationService;
    }

    @Transactional
    public String createUser(UserRequestDTO dto) {
        String email = dto.getEmail().trim().toLowerCase(java.util.Locale.ROOT);

        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
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

        // O envio é síncrono: se o SMTP falhar, a transação de cadastro será revertida.
        emailVerificationService.gerarEEnviarCodigo(user);
        return "Usuário cadastrado. Verifique seu e-mail para ativar a conta.";
    }

    public List<UserEntity> listarTodosUsuarios() {
        return userRepository.findAll();
    }

    public UserEntity buscarPorId(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public UserEntity buscarPorEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public void atualizarUsuario(UUID id, UserRequestDTO dto) {
        UserEntity user = buscarPorId(id);
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    public void deletarUsuario(UUID id) {
        UserEntity usuario = buscarPorId(id);
        // Remover tokens antes do usuário evita falhas por restrição de chave estrangeira.
        emailVerificationService.removerTokensDoUsuario(id);
        userRepository.delete(usuario);
    }
}
