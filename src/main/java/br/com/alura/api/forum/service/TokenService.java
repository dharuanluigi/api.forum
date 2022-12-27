package br.com.alura.api.forum.service;

import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.exceptions.InvalidTokenException;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
@Qualifier("token_implementation")
public class TokenService implements ITokenService {

    @Value("${forum.jwt.secret}")
    private String secret;

    @Value("${forum.jwt.expiration}")
    private Integer expiration;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public String create(User user) {
        try {
            var claims = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
            return JWT
                    .create()
                    .withIssuer("API Forum")
                    .withSubject(user.getEmail())
                    .withArrayClaim("cls", claims)
                    .withExpiresAt(setExpiration())
                    .sign(Algorithm.HMAC256(this.secret));
        } catch (JWTCreationException e) {
            throw new InvalidTokenException("Error when try create auth token, contact system admin");
        }
    }

    public String validate(String jwtToken) {
        try {
            return JWT
                    .require(Algorithm.HMAC256(this.secret))
                    .withIssuer("API Forum")
                    .build()
                    .verify(removeTokenPrefix(jwtToken))
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException("Invalid token. It is expired or is invalid");
        }
    }

    public Boolean isUserTheOwner(User user) {
        var token = httpServletRequest.getHeader("Authorization");
        var userEmail = validate(token);
        return Objects.equals(user.getEmail(), userEmail);
    }

    public Claim getRoles(String jwtToken) {
        try {
            return JWT
                    .require(Algorithm.HMAC256(this.secret))
                    .withIssuer("API Forum")
                    .build()
                    .verify(removeTokenPrefix(jwtToken))
                    .getClaim("cls");
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException("Invalid token. It is expired or is invalid");
        }
    }

    private Instant setExpiration() {
        return LocalDateTime.now().plusHours(this.expiration).toInstant(ZoneOffset.of("-03:00"));
    }

    private String removeTokenPrefix(String fullToken) {
        return fullToken.replace("Bearer ", "");
    }
}
