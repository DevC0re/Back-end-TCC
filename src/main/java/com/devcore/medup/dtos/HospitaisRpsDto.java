package com.devcore.medup.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitaisRpsDto{

    private String name;
    private String address;
    private String phone;

    private String cnpj;
    private String email;
    
}