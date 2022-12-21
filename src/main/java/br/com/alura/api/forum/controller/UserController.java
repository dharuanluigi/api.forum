package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.CreatedUserDTO;
import br.com.alura.api.forum.dto.InsertUserDTO;
import br.com.alura.api.forum.dto.ListUserDTO;
import br.com.alura.api.forum.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<CreatedUserDTO> create(@RequestBody @Valid InsertUserDTO insertUserDTO) {
        var userCreated = userService.create(insertUserDTO);
        return ResponseEntity.ok(userCreated);
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll(@PageableDefault Pageable pagination) {
        var content = userService.findAll(pagination);
        return ResponseEntity.ok(content);
    }
}
