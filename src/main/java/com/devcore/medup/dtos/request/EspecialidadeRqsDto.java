package com.devcore.medup.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecialidadeRqsDto {

    @NotBlank(message = "O nome da especialidade é obrigatório.")
    private String nome;

    private String descricao;
}