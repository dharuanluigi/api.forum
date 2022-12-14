package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.AddedTopicDTO;
import br.com.alura.api.forum.dto.InsertTopicDTO;
import br.com.alura.api.forum.dto.ListTopicsDTO;
import br.com.alura.api.forum.repository.CourseRepository;
import br.com.alura.api.forum.repository.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<ListTopicsDTO> findAllOrByCourseName(String course_name) {
        if (course_name != null) {
            return repository.findByCourse_Name(course_name);
        }
        return repository.findAll().stream().map(ListTopicsDTO::new).toList();
    }

    @PostMapping
    public ResponseEntity<AddedTopicDTO> insert(@RequestBody @Valid InsertTopicDTO insertTopicDTO, UriComponentsBuilder uriBuilder) {
        var topic = insertTopicDTO.toEntity(courseRepository);
        repository.save(topic);
        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new AddedTopicDTO(topic));
    }
}
