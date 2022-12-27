package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.User;
import lombok.Getter;

@Getter
public class DetailsOwnUserDTO extends DetailsUserBaseDTO {
    private final String id;
    private final String name;
    private final String email;

    public DetailsOwnUserDTO(User user) {
        super(user.getUsername(), user.getProfiles());
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();

    }
}
