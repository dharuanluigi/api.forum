package br.com.alura.api.forum.services.interfaces;

import br.com.alura.api.forum.dto.CreatedUserDTO;
import br.com.alura.api.forum.dto.InsertUserDTO;
import br.com.alura.api.forum.dto.ListUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    CreatedUserDTO create(InsertUserDTO insertUserDTO);

    Page<ListUserDTO> findAll(Pageable pagination);

    ListUserDTO findById(Long id);
}
