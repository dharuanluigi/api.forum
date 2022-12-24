package br.com.alura.api.forum.entity;

import br.com.alura.api.forum.dto.UpdateTopicDTO;
import br.com.alura.api.forum.entity.enums.TopicStatus;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) default 'NOT_ANSWERED'")
    private TopicStatus status = TopicStatus.NOT_ANSWERED;

    @ManyToOne
    private User author;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "topic")
    private List<Answer> answers = new ArrayList<>();

    public Topic(String title, String message, Course course, User author) {
        this.title = title;
        this.message = message;
        this.course = course;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id.equals(topic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateData(UpdateTopicDTO updateTopicDTO) {
        if (updateTopicDTO.title() != null) {
            this.title = updateTopicDTO.title();
        }

        if (updateTopicDTO.message() != null) {
            this.message = updateTopicDTO.message();
        }
    }

    public Boolean isUserOwner(HttpServletRequest requestHttp, ITokenService tokenService) {
        var token = requestHttp.getHeader("Authorization");
        var userEmail = tokenService.validate(token);
        return Objects.equals(this.author.getEmail(), userEmail);
    }

    public Boolean isUserModerator(HttpServletRequest requestHttp, ITokenService tokenService) {
        var token = requestHttp.getHeader("Authorization");
        var claims = tokenService.getRoles(token);
        var roles = Arrays.asList(claims.asArray(String.class));
        return roles.contains("ROLE_MODERATOR");
    }

    public void close() {
        this.status = TopicStatus.CLOSED;
    }
}
