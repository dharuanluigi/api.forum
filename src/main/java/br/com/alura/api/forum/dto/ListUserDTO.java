package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.User;

import java.util.List;

public record ListUserDTO(Long id, String email, List<ListProfileDTO> profiles) {
    public ListUserDTO(User user) {
        this(user.getId(), user.getEmail(),
                user.getProfiles().stream().map(p -> new ListProfileDTO(p.getName())).toList());
    }
}
