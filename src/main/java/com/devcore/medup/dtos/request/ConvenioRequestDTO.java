package com.devcore.medup.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class ConvenioRequestDTO {

    @NotBlank(message = "O campo não pode ficar vazio")
    private String name;
    private String hospitaisAceitos;
    @CNPJ(message = "Digite um cnpj válido")
    private String cnpj;
}
