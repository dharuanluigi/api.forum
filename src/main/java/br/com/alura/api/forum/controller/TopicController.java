package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.repository.CourseRepository;
import br.com.alura.api.forum.repository.TopicRepository;
import br.com.alura.api.forum.service.interfaces.ITopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ITopicService service;

    @GetMapping
    @Cacheable(value = "topicAllList")
    public ResponseEntity<Page<ListTopicsDTO>> findAllOrByCourseName(@RequestParam(required = false) String course_name, @PageableDefault Pageable pagination) {
        if (course_name != null) {
            var foundedTopics = repository.findByCourse_Name(course_name, pagination);
            var foundedTopicsDTOs = foundedTopics.map(ListTopicsDTO::new);
            return ResponseEntity.ok(foundedTopicsDTOs);
        }
        var allTopics = repository.findAll(pagination);
        var allTopicsDTOs = allTopics.map(ListTopicsDTO::new);
        return ResponseEntity.ok(allTopicsDTOs);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "topicAllList", allEntries = true)
    public ResponseEntity<AddedTopicDTO> insert(@RequestBody @Valid InsertTopicDTO insertTopicDTO, UriComponentsBuilder uriBuilder) {
        var addedTopic = service.insert(insertTopicDTO);
        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(addedTopic.id()).toUri();
        return ResponseEntity.created(uri).body(addedTopic);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> findById(@PathVariable Long id) {
        var topic = repository.getReferenceById(id);
        var response = new TopicDetailsDTO(topic);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicAllList", allEntries = true)
    public ResponseEntity<TopicDetailsDTO> update(@PathVariable Long id, @RequestBody UpdateTopicDTO updateTopicDTO) {
        var topic = repository.getReferenceById(id);
        topic.updateData(updateTopicDTO);
        return ResponseEntity.ok(new TopicDetailsDTO(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicAllList", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
