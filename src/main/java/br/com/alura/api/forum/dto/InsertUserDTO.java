package br.com.alura.api.forum.dto;

import jakarta.validation.constraints.NotBlank;

public record InsertUserDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password) {}
