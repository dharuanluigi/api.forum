package br.com.alura.api.forum.dto;

import jakarta.validation.constraints.NotBlank;

public record InsertTopicDTO(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String courseName
) {
}
