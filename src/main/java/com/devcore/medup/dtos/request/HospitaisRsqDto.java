package com.devcore.medup.dtos.request;


import com.devcore.medup.dtos.request.EnderecoRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class HospitaisRsqDto {
    
    private String name;
    private String address;
    @Valid
    private EnderecoRequestDTO endereco;
    private String phone;
    private String cnpj;
    private String email;

}