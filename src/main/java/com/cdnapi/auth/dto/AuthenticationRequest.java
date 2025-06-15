package com.cdnapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password can not be blank")
    private String password;
}
