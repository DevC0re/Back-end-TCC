package com.devcore.medup.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoEspecialidadeRpsDto {
    private UUID id;
    private Double nota;
    private String observacao;
    private LocalDate dataAvaliacao;
    private String nomeHospital;
    private String nomeEspecialidade;
}