package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    CreatedUserDTO create(InsertUserDTO insertUserDTO);

    Page<DetailsUserDTO> findAll(Pageable pagination);

    DetailsUserBaseDTO findById(String id);

    UpdatedUserDTO update(String id, UpdateUserDTO updateUserDTO);

    void delete(String id);

    DetailsOwnUserDTO getCurrentUserData();
}
