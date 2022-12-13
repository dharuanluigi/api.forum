package br.com.alura.api.forum.repository;

import br.com.alura.api.forum.dto.ListTopicsDTO;
import br.com.alura.api.forum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<ListTopicsDTO> findByCourse_Name(String courseName);
}
