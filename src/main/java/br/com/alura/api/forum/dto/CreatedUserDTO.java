package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.User;

import java.util.List;

public record CreatedUserDTO(String id, String name, String email, List<ListProfileDTO> profiles) {
    public CreatedUserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(),
                user.getProfiles().stream().map(p -> new ListProfileDTO(p.getName())).toList());
    }
}
