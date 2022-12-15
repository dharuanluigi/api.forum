package br.com.alura.api.forum.configuration;

import br.com.alura.api.forum.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.secret}")
    private String secret;

    @Value("${forum.jwt.expiration}")
    private Long expiration;

    public String createToken(Authentication auth) {
        var user = (User) auth.getPrincipal();
        var now = new Date();
        var expirationInMs = new Date(now.getTime() + this.expiration);

        return Jwts.builder().setIssuer("Forum alura api").setSubject(user.getId().toString()).setIssuedAt(now).setExpiration(expirationInMs).signWith(SignatureAlgorithm.HS256, this.secret).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
