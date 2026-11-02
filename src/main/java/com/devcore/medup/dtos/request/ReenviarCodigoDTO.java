package com.devcore.medup.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ReenviarCodigoDTO {
    @Email
    @NotBlank
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
