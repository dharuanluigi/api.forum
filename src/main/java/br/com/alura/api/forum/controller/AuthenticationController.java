package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.configuration.TokenService;
import br.com.alura.api.forum.dto.LoginDTO;
import br.com.alura.api.forum.dto.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginDTO loginDto) {
        UsernamePasswordAuthenticationToken loginData = loginDto.generateTokenData();
        var auth = authManager.authenticate(loginData);
        var token = tokenService.createToken(auth);
        return ResponseEntity.ok(new TokenDTO("Bearer", token));
    }
}
