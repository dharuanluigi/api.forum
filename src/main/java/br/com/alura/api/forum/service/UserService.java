package br.com.alura.api.forum.service;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.exceptions.UpdateForbiddenException;
import br.com.alura.api.forum.repository.ProfileRepository;
import br.com.alura.api.forum.repository.UserRepository;
import br.com.alura.api.forum.service.interfaces.ITokenService;
import br.com.alura.api.forum.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ITokenService tokenService;

    @Override
    @Transactional
    public CreatedUserDTO create(InsertUserDTO insertUserDTO) {
        var profiles = profileRepository.findByName("ROLE_USER");
        var user = userRepository.save(new User(null, insertUserDTO.name(), insertUserDTO.email(),
                new BCryptPasswordEncoder(10).encode(insertUserDTO.password()), List.of(profiles)));
        return new CreatedUserDTO(user);
    }

    @Override
    public Page<ListUserDTO> findAll(Pageable pagination) {
        var foundedUsers = userRepository.findAll(pagination);
        return foundedUsers.map(u -> new ListUserDTO(u.getId(), u.getEmail(),
                u.getProfiles().stream().map(p -> new ListProfileDTO(p.getName())).toList()));
    }

    @Override
    public ListUserDTO findById(String id) {
        var user = userRepository.getReferenceById(id);
        return new ListUserDTO(user);
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
        userRepository.deleteById(id);
    }
}