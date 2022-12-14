package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Topic;

import java.time.LocalDateTime;

public record AddedTopicDTO(Long id, String title, String message, LocalDateTime createdAt) {
    public AddedTopicDTO(Topic entity) {
        this(entity.getId(), entity.getTitle(), entity.getMessage(), entity.getCreatedAt());
    }
}
