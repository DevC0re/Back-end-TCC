package com.devcore.medup.entities;

import com.devcore.medup.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String cpf;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    private String photo;

    // Clientes já cadastrados permanecem ativos; novos cadastros são explicitamente inativos.
    // Ver README-INTEGRACAO.md para migração dos registros existentes.
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "convenio_id")
    private ConvenioEntity convenio;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoEntity> enderecos = new ArrayList<>();
}
