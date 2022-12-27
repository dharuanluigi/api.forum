package br.com.alura.api.forum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InsertUserDTO(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
}
