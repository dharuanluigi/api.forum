package br.com.alura.api.forum.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
	private Long id;
	private String message;
	private Topic topic;
	private LocalDateTime createdAt = LocalDateTime.now();
	private User author;
	private Boolean isSolved = false;
}
