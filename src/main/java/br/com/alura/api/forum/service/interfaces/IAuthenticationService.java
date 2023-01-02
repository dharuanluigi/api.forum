package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.entity.User;

public interface IAuthenticationService {

    User loginUser(String email, String password);
}
