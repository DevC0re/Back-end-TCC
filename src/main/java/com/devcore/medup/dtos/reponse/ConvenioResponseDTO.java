package com.devcore.medup.dtos.reponse;

import lombok.Data;

import java.util.List;

@Data
public class ConvenioResponseDTO {

    private String name;
    private List<String> hospitaisAceitos;
    private String cnpj;
}
