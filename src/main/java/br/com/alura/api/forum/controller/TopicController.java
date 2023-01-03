package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.repository.CourseRepository;
import br.com.alura.api.forum.repository.TopicRepository;
import br.com.alura.api.forum.service.interfaces.ITopicService;
import br.com.alura.api.forum.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    //@Cacheable(value = "topicAllList")
    public ResponseEntity<Page<ListTopicsDTO>> findAllWithFilters(@RequestParam(required = false) String course_name, @PageableDefault Pageable pagination) {
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
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)})
    //@CacheEvict(value = "topicAllList", allEntries = true)
    public ResponseEntity<AddedTopicDTO> insert(@RequestBody @Valid InsertTopicDTO insertTopicDTO, UriComponentsBuilder uriBuilder) {
        var addedTopic = service.insert(insertTopicDTO);
        var uri = uriBuilder.path("/topics/{id}").buildAndExpand(addedTopic.id()).toUri();
        return ResponseEntity.created(uri).body(addedTopic);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> findById(@PathVariable String id) {
        var topic = repository.getReferenceById(id);
        var response = new TopicDetailsDTO(topic);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)})
    //@CacheEvict(value = "topicAllList", allEntries = true)
    public ResponseEntity<TopicDetailsDTO> update(@PathVariable String id, @RequestBody UpdateTopicDTO updateTopicDTO) {
        var updatedTopic = service.update(id, updateTopicDTO);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)})
    //@CacheEvict(value = "topicAllList", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/close/{id}")
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)})
    public ResponseEntity<Void> close(@PathVariable String id) {
        service.close(id);
        return ResponseEntity.noContent().build();
    }
}
