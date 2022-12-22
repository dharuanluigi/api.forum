package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.util.StringFormats;

import java.util.List;

public record CreatedUserDTO(Long id, String name, String email, List<ListProfileDTO> profiles) {
    public CreatedUserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(),
                user.getProfiles().stream().map(p -> new ListProfileDTO(StringFormats.removePrefixRoleName(p.getName()))).toList());
    }
}
