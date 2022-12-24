package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Topic;
import br.com.alura.api.forum.entity.enums.TopicStatus;

import java.time.LocalDateTime;

public record ListTopicsDTO(String id, String title, String message, LocalDateTime createdAt, TopicStatus status) {
    public ListTopicsDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreatedAt(), topic.getStatus());
    }
}
