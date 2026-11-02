package com.devcore.medup.dtos.reponse;

import java.util.UUID;
import com.devcore.medup.dtos.reponse.EnderecoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitaisRpsDto {

    private UUID id;
    private String name;
    private String address;
    private EnderecoResponseDTO endereco;
    private String phone;
    private String cnpj;
    private String email;
}