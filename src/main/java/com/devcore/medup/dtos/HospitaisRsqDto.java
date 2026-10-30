package com.devcore.medup.dtos;


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
    private String phone;
    private String cnpj;
    private String email;

}