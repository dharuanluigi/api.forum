package br.com.alura.api.forum.service;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.entity.UserActivation;
import br.com.alura.api.forum.exceptions.DeleteForbiddenException;
import br.com.alura.api.forum.exceptions.InvalidTokenException;
import br.com.alura.api.forum.exceptions.UpdateForbiddenException;
import br.com.alura.api.forum.repository.ProfileRepository;
import br.com.alura.api.forum.repository.UserActivationRepository;
import br.com.alura.api.forum.repository.UserRepository;
import br.com.alura.api.forum.service.interfaces.IAuthenticationService;
import br.com.alura.api.forum.service.interfaces.IEmailService;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import br.com.alura.api.forum.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    IEmailService emailService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private UserActivationRepository userActivationRepository;
    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private Faker faker;

    @Override
    @Transactional
    public User create(InsertUserDTO insertUserDTO) {
        var profiles = profileRepository.findByName("ROLE_USER");
        return userRepository.save(new User(null, insertUserDTO.name(), faker.superhero().prefix(), insertUserDTO.email(),
                new BCryptPasswordEncoder(10).encode(insertUserDTO.password()), List.of(profiles), false));
    }

    @Override
    @Transactional
    public void activate(String code) {
        var activation = userActivationRepository.findByCode(code);

        if (activation == null) {
            throw new InvalidTokenException("The activation code is invalid!");
        }

        if (activation.isExpired()) {
            throw new InvalidTokenException("Activation code is expired!");
        }

        var user = userRepository.getReferenceById(activation.getUser().getId());
        user.enableAccount();
        userActivationRepository.delete(activation);
    }

    @Override
    public Page<DetailsUserDTO> findAll(Pageable pagination) {
        var foundedUsers = userRepository.findAll(pagination);
        return foundedUsers.map(DetailsUserDTO::new);
    }

    @Override
    public DetailsUserBaseDTO findById(String id) {
        var user = userRepository.getReferenceById(id);

        if (tokenService.isUserTheOwner(user)) {
            return new DetailsOwnUserDTO(user);
        }

        return new DetailsUserDTO(user);
    }

    @Override
    public DetailsOwnUserDTO getCurrentUserData() {
        var token = httpServletRequest.getHeader("Authorization");
        var email = tokenService.validate(token);
        var user = userRepository.findByEmail(email);
        return new DetailsOwnUserDTO(user);
    }

    @Override
    @Transactional
    public UpdatedUserDTO update(String id, UpdateUserDTO updateUserDTO) {
        var user = userRepository.getReferenceById(id);

        if (tokenService.isUserTheOwner(user)) {
            user.update(updateUserDTO);
            return new UpdatedUserDTO(user);
        }

        throw new UpdateForbiddenException("Just the owner of data can update your own data");
    }

    @Override
    @Transactional
    public void delete(String id) {
        var user = userRepository.getReferenceById(id);

        if (tokenService.isUserTheOwner(user) || tokenService.isUserModerator()) {
            userRepository.deleteById(id);
        } else {
            throw new DeleteForbiddenException("Just the account owner can delete it self");
        }
    }

    @Override
    public void resendActivationCode(String email, String password) {
        var user = authenticationService.loginUser(email, password);

        if (user.getIsActive()) {
            throw new IllegalArgumentException("User already activated");
        }

        var expiredCode = userActivationRepository.findByUserId(user.getId());

        if (expiredCode != null) {
            userActivationRepository.delete(expiredCode);
        }

        var activationCode = generateActivationCode(user);
        emailService.sendActivationCode(user.getEmail(), activationCode);
    }

    public String generateActivationCode(User user) {
        var activationCode = String.valueOf(Instant.now().getNano());
        var activation = new UserActivation(activationCode, Instant.now(), user);
        userActivationRepository.save(activation);
        return activationCode;
    }
}