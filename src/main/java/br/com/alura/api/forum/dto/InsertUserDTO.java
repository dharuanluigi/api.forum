package br.com.alura.api.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record InsertUserDTO(
        @NotBlank
        String name,

        @NotBlank
        String username,

        @NotBlank
        @Pattern(regexp = "(.*@(gmail|hotmail|outlook|aol|yahoo|ymail).com)|(.*@proton.me)", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email domain. Just some domains is accepted, like: gmail, hotmail, outlook, proton, aol and yahoo or ymail.")
        String email,
        @NotBlank
        String password
) {
}
