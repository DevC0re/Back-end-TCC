package com.devcore.medup.dtos.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EnderecoRequestDTO {
    @NotBlank
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve conter 8 dígitos, com ou sem hífen")
    private String cep;

    @NotBlank @Size(max = 180)
    private String logradouro;

    @NotBlank @Size(max = 20)
    private String numero;

    @NotBlank @Size(max = 100)
    private String bairro;

    @NotBlank @Size(max = 100)
    private String cidade;

    @NotBlank
    @Pattern(regexp = "(?i)[A-Z]{2}", message = "Estado deve ser a sigla UF de duas letras")
    private String estado;

    @Size(max = 150)
    private String complemento;

    @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    private BigDecimal longitude;

    @Size(max = 60)
    private String apelido;

    // Opcional: ao criar o primeiro endereço, ele será principal automaticamente.
    private Boolean principal;
}
