package com.devcore.medup.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "hospitais")
public class HospitaisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String address;
    private String phone;

    @CNPJ
    private String cnpj;

    @Email
    private String email;

    @ManyToMany(mappedBy = "hospitais")
    private Set<ConvenioEntity> convenios = new HashSet<>();
}