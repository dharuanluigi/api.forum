package br.com.alura.api.forum.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Objects;


@DataJpaTest
// when needs substitute H2 default test database
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CourseRepository repository;

    @Test
    void shouldReturnACourseWithValidNameStorage() {
        var course = repository.findByName("HTML 5");
        Assert.notNull(course, "Course was get by name is null");
    }

    @Test
    void shouldReturnACourseWithTheSameNameWasStorage() {
        var course = repository.findByName("HTML 5");
        Assert.isTrue(Objects.equals(course.getName(), "HTML 5"), "The course was looking for is different was provided");
    }

    @Test
    void shouldNOTReturnACourseWithInvalidNameStorage() {
        var course = repository.findByName("iii");
        Assert.isNull(course, "Course was found, but didn't");
    }
}
