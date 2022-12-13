package br.com.alura.api.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "tb_course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String category;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Course course = (Course) o;
		return id != null && Objects.equals(id, course.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
