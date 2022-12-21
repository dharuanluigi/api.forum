package br.com.alura.api.forum.dto;

import java.util.List;

public record ListUserDTO(Long id, String email, List<ListProfileDTO> profiles) {}
