package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    User create(InsertUserDTO insertUserDTO);

    void activate(String code);

    Page<DetailsUserDTO> findAll(Pageable pagination);

    DetailsUserBaseDTO findById(String id);

    UpdatedUserDTO update(String id, UpdateUserDTO updateUserDTO);

    void delete(String id);

    DetailsOwnUserDTO getCurrentUserData();

    void activeOldAccount(String email, String password);

    String generateActivationCode(User user);
}
