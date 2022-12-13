package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.ListTopicsDTO;
import br.com.alura.api.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository repository;

    @GetMapping
    public List<ListTopicsDTO> findAll(@RequestParam(defaultValue = "course_name") String course_name) {
        if (course_name != null) {
            return repository.findByCourse_Name(course_name);
        }

        return repository.findAll().stream().map(ListTopicsDTO::new).toList();
    }
}
