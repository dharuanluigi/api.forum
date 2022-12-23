package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.LoginDTO;
import br.com.alura.api.forum.dto.TokenDTO;
import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Profile({"prod", "test"})
public class AuthenticationController {
    @Autowired
    private ITokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginDTO loginDto) {
        var userToken = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        var authentication = authenticationManager.authenticate(userToken);
        var token = tokenService.create((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO("Bearer", token));
    }
}
