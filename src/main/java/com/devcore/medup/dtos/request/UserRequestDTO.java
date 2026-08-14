package com.devcore.medup.dtos.request;

import com.devcore.medup.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "O campo não pode ficar vazio")
    private String name;
    @Email(message = "Digite um email válido")
    @NotBlank(message = "O campo não pode ficar vazio")
    private String email;
    @CPF(message = "Digite um cpf válido")
    private String cpf;
    @NotBlank(message = "O campo não pode ficar vazio")
    @Size(min = 8, max = 16, message = "A senha deve ter entre 8 e 16 caracteres")
    private String password;
    private Roles roles;
    private String photo;

    public UserRequestDTO(String name, String email, String cpf, String password) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
    }
}
