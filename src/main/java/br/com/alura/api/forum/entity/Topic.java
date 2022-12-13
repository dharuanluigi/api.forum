package br.com.alura.api.forum.entity;

import br.com.alura.api.forum.entity.enums.TopicStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
	private Long id;
	private String title;
	private String message;
	private LocalDateTime createdAt = LocalDateTime.now();
	private TopicStatus status = TopicStatus.NOT_ANSWERED;
	private User author;
	private Course course;
	private List<Answer> answers = new ArrayList<>();
}
