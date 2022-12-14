package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Topic;
import br.com.alura.api.forum.entity.enums.TopicStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record TopicDetailsDTO(Long id, String title, String message, LocalDateTime createdAt, String author, TopicStatus status, List<AnswerDTO> answers) {
    public TopicDetailsDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreatedAt(), topic.getAuthor().getName(), topic.getStatus(), new ArrayList<>(topic.getAnswers().stream().map(AnswerDTO::new).toList()));
    }
}
