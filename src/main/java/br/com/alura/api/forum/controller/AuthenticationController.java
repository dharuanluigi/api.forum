package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.LoginDTO;
import br.com.alura.api.forum.dto.TokenDTO;
import br.com.alura.api.forum.exceptions.ForbiddenRequestException;
import br.com.alura.api.forum.service.interfaces.IAuthenticationService;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Generate a access token to make actions into forum api")
public class AuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private ITokenService tokenService;

    @PostMapping
    @Operation(summary = "Login", description = "Makes a registered login into api")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDto) {
        var user = authenticationService.loginUser(loginDto.email(), loginDto.password());

        if (user != null && user.getIsActive()) {
            var token = tokenService.create(user);
            return ResponseEntity.ok(new TokenDTO("Bearer", token));
        }

        throw new ForbiddenRequestException("User can't make login, contact system admin");
    }
}
