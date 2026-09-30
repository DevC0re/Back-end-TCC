package com.devcore.medup.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

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
}