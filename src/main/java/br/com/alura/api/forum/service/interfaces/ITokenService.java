package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.entity.User;
import com.auth0.jwt.interfaces.Claim;

public interface ITokenService {

    String create(User user);

    String validate(String jwtToken);

    Claim getRoles(String jwtToken);

    Boolean isUserTheOwner(User user);

    Boolean isUserModerator();
}
