package br.com.alura.api.forum.middleware;

import br.com.alura.api.forum.configuration.TokenService;
import br.com.alura.api.forum.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationMiddleware extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AuthenticationMiddleware(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        var isValidToken = tokenService.validateToken(token);

        if (isValidToken) {
            authenticateClient(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateClient(String token) {
        var id = tokenService.getUserId(token);
        var browseUser = userRepository.findById(id);
        if (browseUser.isPresent()) {
            var user = browseUser.get();
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || token.isBlank() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.replace("Bearer ", "");
    }
}
