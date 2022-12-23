package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.entity.User;

public interface ITokenService {

    String create(User user);

    String validate(String jwtToken);
}
