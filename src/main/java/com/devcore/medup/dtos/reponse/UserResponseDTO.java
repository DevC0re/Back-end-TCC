package com.devcore.medup.dtos.reponse;

import com.devcore.medup.enums.Roles;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserResponseDTO {

    private String name;
    private String email;
    private String cpf;
    private String password;

    public UserResponseDTO(String name, String email, String photo) {
    }
}
