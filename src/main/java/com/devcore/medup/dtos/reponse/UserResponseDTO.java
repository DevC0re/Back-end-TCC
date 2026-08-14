package com.devcore.medup.dtos.reponse;

import com.devcore.medup.enums.Roles;
import lombok.Data;

@Data
public class UserResponseDTO {

    private String name;
    private String email;
    private String cpf;
    private String password;
    private Roles roles;
    private String photo;
}
