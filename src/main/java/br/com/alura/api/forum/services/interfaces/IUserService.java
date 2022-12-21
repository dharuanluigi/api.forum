package br.com.alura.api.forum.services.interfaces;

import br.com.alura.api.forum.dto.CreatedUserDTO;
import br.com.alura.api.forum.dto.InsertUserDTO;

public interface IUserService {

    CreatedUserDTO create(InsertUserDTO insertUserDTO);
}
