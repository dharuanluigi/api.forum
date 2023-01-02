package br.com.alura.api.forum.service;

import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.service.interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User loginUser(String email, String password) {
        var userToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(userToken);
        return (User) authentication.getPrincipal();
    }
}
