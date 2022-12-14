package br.com.alura.api.forum.repository;

import br.com.alura.api.forum.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByCourse_Name(String courseName, Pageable pagination);
}
