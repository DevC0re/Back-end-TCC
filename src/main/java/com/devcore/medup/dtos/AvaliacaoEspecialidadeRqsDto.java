package com.devcore.medup.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AvaliacaoEspecialidadeRqsDto {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.0")
    @Max(value = 5, message = "A nota máxima é 5.0")
    private Double nota;

    private String observacao;

    @NotNull(message = "O ID do hospital é obrigatório.")
    private UUID hospitalId;

    @NotNull(message = "O ID da especialidade é obrigatório.")
    private UUID especialidadeId;
}