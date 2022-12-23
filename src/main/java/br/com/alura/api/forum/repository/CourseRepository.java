package br.com.alura.api.forum.repository;

import br.com.alura.api.forum.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByName(String name);
}
