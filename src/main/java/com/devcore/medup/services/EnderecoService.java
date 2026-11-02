package com.devcore.medup.services;

import com.devcore.medup.dtos.reponse.EnderecoResponseDTO;
import com.devcore.medup.dtos.request.EnderecoRequestDTO;
import com.devcore.medup.entities.EnderecoEntity;
import com.devcore.medup.entities.HospitaisEntity;
import com.devcore.medup.entities.UserEntity;
import com.devcore.medup.repositories.EnderecoRepository;
import com.devcore.medup.repositories.HospitaisRepository;
import com.devcore.medup.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecos;
    private final UserRepository usuarios;
    private final HospitaisRepository hospitais;

    public EnderecoService(EnderecoRepository enderecos,
                           UserRepository usuarios,
                           HospitaisRepository hospitais) {
        this.enderecos = enderecos;
        this.usuarios = usuarios;
        this.hospitais = hospitais;
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> listarMeus(Authentication auth) {
        UserEntity usuario = usuarioAutenticado(auth);
        return enderecos.findByUsuario_IdOrderByPrincipalDesc(usuario.getId())
                .stream().map(this::converter).toList();
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarMeu(UUID id, Authentication auth) {
        UserEntity usuario = usuarioAutenticado(auth);
        return converter(enderecoDoUsuario(id, usuario.getId()));
    }

    @Transactional
    public EnderecoResponseDTO criarMeu(EnderecoRequestDTO dto, Authentication auth) {
        UserEntity usuario = usuarioAutenticado(auth);
        List<EnderecoEntity> atuais = enderecos.findByUsuario_IdOrderByPrincipalDesc(usuario.getId());
        boolean principal = atuais.isEmpty() || Boolean.TRUE.equals(dto.getPrincipal());
        if (principal) {
            atuais.forEach(e -> e.setPrincipal(false));
        }
        EnderecoEntity endereco = new EnderecoEntity();
        preencher(endereco, dto);
        endereco.setUsuario(usuario);
        endereco.setPrincipal(principal);
        return converter(enderecos.save(endereco));
    }

    @Transactional
    public EnderecoResponseDTO atualizarMeu(UUID id, EnderecoRequestDTO dto, Authentication auth) {
        UserEntity usuario = usuarioAutenticado(auth);
        EnderecoEntity endereco = enderecoDoUsuario(id, usuario.getId());
        if (Boolean.FALSE.equals(dto.getPrincipal()) && endereco.isPrincipal()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Escolha outro endereço principal antes de desmarcar o atual.");
        }
        if (Boolean.TRUE.equals(dto.getPrincipal())) {
            enderecos.findByUsuario_IdOrderByPrincipalDesc(usuario.getId())
                    .forEach(e -> e.setPrincipal(e.getId().equals(endereco.getId())));
        }
        preencher(endereco, dto);
        return converter(endereco);
    }

    @Transactional
    public EnderecoResponseDTO definirPrincipal(UUID id, Authentication auth) {
        UserEntity usuario = usuarioAutenticado(auth);
        EnderecoEntity escolhido = enderecoDoUsuario(id, usuario.getId());
        enderecos.findByUsuario_IdOrderByPrincipalDesc(usuario.getId())
                .forEach(e -> e.setPrincipal(e.getId().equals(escolhido.getId())));
        return converter(escolhido);
    }

    @Transactional
    public void excluirMeu(UUID id, Authentication auth) {
        UserEntity usuario = usuarioAutenticado(auth);
        EnderecoEntity endereco = enderecoDoUsuario(id, usuario.getId());
        boolean eraPrincipal = endereco.isPrincipal();
        enderecos.delete(endereco);
        enderecos.flush();
        if (eraPrincipal) {
            List<EnderecoEntity> restantes = enderecos.findByUsuario_IdOrderByPrincipalDesc(usuario.getId());
            if (!restantes.isEmpty()) {
                restantes.get(0).setPrincipal(true);
            }
        }
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarHospital(UUID hospitalId) {
        if (!hospitais.existsById(hospitalId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital não encontrado.");
        }
        EnderecoEntity endereco = enderecos.findByHospital_Id(hospitalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Hospital ainda não possui endereço estruturado."));
        return converter(endereco);
    }

    // Usado pelo DTO de resposta dos hospitais; os dados antigos em String seguem intactos.
    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarHospitalOuNulo(UUID hospitalId) {
        return enderecos.findByHospital_Id(hospitalId).map(this::converter).orElse(null);
    }

    @Transactional
    public EnderecoResponseDTO salvarHospital(UUID hospitalId, EnderecoRequestDTO dto) {
        HospitaisEntity hospital = hospitais.findById(hospitalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital não encontrado."));
        EnderecoEntity endereco = enderecos.findByHospital_Id(hospitalId)
                .orElseGet(EnderecoEntity::new);
        preencher(endereco, dto);
        endereco.setHospital(hospital);
        endereco.setUsuario(null);
        endereco.setApelido(null);
        endereco.setPrincipal(false);
        return converter(enderecos.save(endereco));
    }

    private UserEntity usuarioAutenticado(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticação necessária.");
        }
        return usuarios.findByEmailIgnoreCase(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Usuário autenticado não encontrado."));
    }

    private EnderecoEntity enderecoDoUsuario(UUID enderecoId, UUID usuarioId) {
        return enderecos.findByIdAndUsuario_Id(enderecoId, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Endereço não encontrado para este usuário."));
    }

    private void preencher(EnderecoEntity e, EnderecoRequestDTO dto) {
        e.setCep(dto.getCep().trim());
        e.setLogradouro(dto.getLogradouro().trim());
        e.setNumero(dto.getNumero().trim());
        e.setBairro(dto.getBairro().trim());
        e.setCidade(dto.getCidade().trim());
        e.setEstado(dto.getEstado().trim().toUpperCase(Locale.ROOT));
        e.setComplemento(dto.getComplemento() == null ? null : dto.getComplemento().trim());
        e.setLatitude(dto.getLatitude());
        e.setLongitude(dto.getLongitude());
        e.setApelido(dto.getApelido() == null ? null : dto.getApelido().trim());
    }

    private EnderecoResponseDTO converter(EnderecoEntity e) {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setId(e.getId());
        dto.setCep(e.getCep());
        dto.setLogradouro(e.getLogradouro());
        dto.setNumero(e.getNumero());
        dto.setBairro(e.getBairro());
        dto.setCidade(e.getCidade());
        dto.setEstado(e.getEstado());
        dto.setComplemento(e.getComplemento());
        dto.setLatitude(e.getLatitude());
        dto.setLongitude(e.getLongitude());
        dto.setApelido(e.getApelido());
        dto.setPrincipal(e.isPrincipal());
        return dto;
    }
}
