package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.LoginDTO;
import br.com.alura.api.forum.dto.TokenDTO;
import br.com.alura.api.forum.exceptions.ForbiddenRequestException;
import br.com.alura.api.forum.service.interfaces.IAuthenticationService;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Profile({"prod", "test"})
public class AuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private ITokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDto) {
        var user = authenticationService.loginUser(loginDto.email(), loginDto.password());

        if (user != null && user.getIsActive()) {
            var token = tokenService.create(user);
            return ResponseEntity.ok(new TokenDTO("Bearer", token));
        }

        throw new ForbiddenRequestException("User can't make login, contact system admin");
    }
}
