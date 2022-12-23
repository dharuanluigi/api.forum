package br.com.alura.api.forum.filter;

import br.com.alura.api.forum.repository.UserRepository;
import br.com.alura.api.forum.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = tryGetAuthorizationFromRequestHeader(request);

        if (token != null) {
            var subject = tokenService.validate(token);
            authenticateClient(subject);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateClient(String subject) {
        var user = userRepository.findByEmail(subject);
        if (user != null) {
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String tryGetAuthorizationFromRequestHeader(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token;
    }
}
