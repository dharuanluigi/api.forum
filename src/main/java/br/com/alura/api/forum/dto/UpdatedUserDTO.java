package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.User;

import java.util.List;

public record UpdatedUserDTO(String id, String name, String email, List<ListProfileDTO> profiles) {
    public UpdatedUserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(),
                user.getProfiles().stream().map(u -> new ListProfileDTO(u.getName())).toList());
    }
}
