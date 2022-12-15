package br.com.alura.api.forum.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginDTO(String email, String password) {
    public UsernamePasswordAuthenticationToken generateTokenData() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
