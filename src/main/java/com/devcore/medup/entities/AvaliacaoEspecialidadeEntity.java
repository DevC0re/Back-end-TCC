package com.devcore.medup.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "avaliacoes_especialidades")
public class AvaliacaoEspecialidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double nota; // Nota de 1.0 a 5.0

    private String observacao;

    private LocalDate dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitaisEntity hospital;

    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private EspecialidadeEntity especialidade;
}