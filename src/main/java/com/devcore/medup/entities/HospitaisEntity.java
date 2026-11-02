package com.devcore.medup.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnderecoEntity endereco;
    private String phone;

    @CNPJ
    private String cnpj;

    @Email
    private String email;

    @ManyToMany(mappedBy = "hospitais")
    private Set<ConvenioEntity> convenios = new HashSet<>();
}