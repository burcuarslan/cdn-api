package com.cdnapi.user.dto;

import com.cdnapi.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçersiz email formatı")
    private String email;

    private String name;

    @NotBlank(message = "Şifre boş olamaz")
    private String password;

    private String description;

    private Set<Role> roles;
}
