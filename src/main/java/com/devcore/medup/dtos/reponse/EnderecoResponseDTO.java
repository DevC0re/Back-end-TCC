package com.devcore.medup.dtos.reponse;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class EnderecoResponseDTO {
    private UUID id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String apelido;
    private boolean principal;
}
