package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.Profile;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class DetailsUserBaseDTO {
    private final String username;

    private final List<ListProfileDTO> profiles;

    public DetailsUserBaseDTO(String username, List<Profile> profiles) {
        this.username = username;
        this.profiles = profiles.stream().map(p -> new ListProfileDTO(p.getName())).toList();
    }
}
