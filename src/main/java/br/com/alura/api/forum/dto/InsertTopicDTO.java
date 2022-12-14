package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Topic;
import br.com.alura.api.forum.repository.CourseRepository;

public record InsertTopicDTO(String title, String message, String courseName) {
    public Topic toEntity(CourseRepository repository) {
        var course = repository.findByName(this.courseName);
        return new Topic(this.title, this.message, course);
    }
}
