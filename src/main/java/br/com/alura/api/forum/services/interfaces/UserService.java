package br.com.alura.api.forum.services.interfaces;

import br.com.alura.api.forum.dto.CreatedUserDTO;
import br.com.alura.api.forum.dto.InsertUserDTO;
import br.com.alura.api.forum.entity.User;
import br.com.alura.api.forum.repository.ProfileRepository;
import br.com.alura.api.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public CreatedUserDTO create(InsertUserDTO insertUserDTO) {
        var profiles = profileRepository.findByName("ROLE_USER");
        var user = userRepository.save(new User(null, insertUserDTO.name(), insertUserDTO.email(),
                insertUserDTO.password(), List.of(profiles)));
        return new CreatedUserDTO(user);
    }
}
