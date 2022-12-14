package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Topic;
import br.com.alura.api.forum.repository.CourseRepository;
import jakarta.validation.constraints.NotBlank;

public record InsertTopicDTO(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String courseName
) {
    public Topic toEntity(CourseRepository repository) {
        var course = repository.findByName(this.courseName);
        return new Topic(this.title, this.message, course);
    }
}
