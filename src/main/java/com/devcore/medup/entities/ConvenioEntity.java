package com.devcore.medup.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class ConvenioEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String hospitaisAceitos;
    @Column(unique = true)
    private String cnpj;

    @ManyToMany
    @JoinTable(
            name = "convenio_has_hospital",
            joinColumns = @JoinColumn(name = "convenio_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id")
    )
    private Set<HospitaisEntity> hospitais = new HashSet<>();

    @OneToMany(mappedBy = "convenio")
    private List<UserEntity> usuarios = new ArrayList<>();
}
