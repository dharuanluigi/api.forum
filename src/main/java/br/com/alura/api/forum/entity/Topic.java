package br.com.alura.api.forum.entity;

import br.com.alura.api.forum.entity.enums.TopicStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String message;
	private LocalDateTime createdAt = LocalDateTime.now();
	@Enumerated(EnumType.STRING)
	private TopicStatus status = TopicStatus.NOT_ANSWERED;
	@ManyToOne
	private User author;
	@ManyToOne
	private Course course;
	@OneToMany(mappedBy = "topic")
	private List<Answer> answers = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Topic topic = (Topic) o;
		return id != null && Objects.equals(id, topic.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
