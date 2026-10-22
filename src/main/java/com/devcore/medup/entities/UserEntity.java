package com.devcore.medup.entities;

import com.devcore.medup.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String cpf;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    private String photo;

    // Usuários recém-cadastrados ficam inativos até confirmar o e-mail.
    @Column(nullable = false)
    private boolean active = false;
}
