package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<CreatedUserDTO> create(@RequestBody @Valid InsertUserDTO insertUserDTO,
                                                 UriComponentsBuilder uriBuilder) {
        var userCreated = userService.create(insertUserDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userCreated.id()).toUri();
        return ResponseEntity.created(uri).body(userCreated);
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> findAll(@PageableDefault Pageable pagination) {
        var allUsers = userService.findAll(pagination);
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListUserDTO> findById(@PathVariable Long id) {
        var foundedUser = userService.findById(id);
        return ResponseEntity.ok(foundedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedUserDTO> update(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        var user = userService.update(id, updateUserDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
