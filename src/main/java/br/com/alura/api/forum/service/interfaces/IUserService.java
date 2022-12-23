package br.com.alura.api.forum.service.interfaces;

import br.com.alura.api.forum.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    CreatedUserDTO create(InsertUserDTO insertUserDTO);

    Page<ListUserDTO> findAll(Pageable pagination);

    ListUserDTO findById(Long id);

    UpdatedUserDTO update(Long id, UpdateUserDTO updateUserDTO);

    void delete(Long id);
}
