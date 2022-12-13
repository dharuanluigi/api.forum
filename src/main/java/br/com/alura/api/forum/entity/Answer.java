package br.com.alura.api.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_answer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String message;
	@ManyToOne
	private Topic topic;
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne
	private User author;
	private Boolean isSolved = false;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Answer answer = (Answer) o;
		return id != null && Objects.equals(id, answer.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
