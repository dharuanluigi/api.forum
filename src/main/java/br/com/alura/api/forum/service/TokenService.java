package br.com.alura.api.forum.service;

import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Qualifier("token_implementation")
public class TokenService implements ITokenService {

    @Value("${forum.jwt.secret}")
    private String secret;

    @Value("${forum.jwt.expiration}")
    private Integer expiration;

    public String create(User user) {
        try {
            return JWT.create().withIssuer("API Forum").withSubject(user.getEmail()).withExpiresAt(setExpiration()).sign(Algorithm.HMAC256(this.secret));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error when try create auth token, contact system admin", e);
        }
    }

    public String validate(String jwtToken) {
        try {
            return JWT.require(Algorithm.HMAC256(this.secret)).withIssuer("API Forum").build().verify(removeTokenPrefix(jwtToken)).getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid jwt");
        }
    }

    private Instant setExpiration() {
        return LocalDateTime.now().plusHours(this.expiration).toInstant(ZoneOffset.of("-03:00"));
    }

    private String removeTokenPrefix(String fullToken) {
        return fullToken.replace("Bearer ", "");
    }
}
