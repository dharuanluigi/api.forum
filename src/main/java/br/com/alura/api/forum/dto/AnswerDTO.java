package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Answer;

import java.time.LocalDateTime;

public record AnswerDTO(Long id, String message, LocalDateTime createdAt, String author) {
    public AnswerDTO(Answer entity) {
        this(entity.getId(), entity.getMessage(), entity.getCreatedAt(), entity.getAuthor().getName());
    }
}
