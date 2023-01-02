package br.com.alura.api.forum.dto;

import br.com.alura.api.forum.entity.User;
import lombok.Getter;

@Getter
public class DetailsUserDTO extends DetailsUserBaseDTO {

    public DetailsUserDTO(User user) {
        super(user.getUsername(), user.getProfiles());
    }
}
