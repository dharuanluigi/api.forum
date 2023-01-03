package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.service.interfaces.IEmailService;
import br.com.alura.api.forum.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "Manage what features the user can doing")
public class UserController {

    private final String SECURITY_HEADER_VALUE = "Authorization";

    @Autowired
    private IUserService userService;

    @Autowired
    private IEmailService emailService;

    @PostMapping
    @Operation(summary = "Creates an user", description = "Endpoint to creates a user access into forum by unique email")
    public ResponseEntity<CreatedUserDTO> create(@RequestBody @Valid InsertUserDTO insertUserDTO,
                                                 UriComponentsBuilder uriBuilder) {
        var user = userService.create(insertUserDTO);

        var activationCode = userService.generateActivationCode(user);
        emailService.sendActivationCode(user.getEmail(), activationCode);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        var createdUserDto = new CreatedUserDTO(user);
        return ResponseEntity.created(uri).body(createdUserDto);
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = SECURITY_HEADER_VALUE)})
    public ResponseEntity<Page<DetailsUserDTO>> findAll(@PageableDefault Pageable pagination) {
        var allUsers = userService.findAll(pagination);
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = SECURITY_HEADER_VALUE)})
    public ResponseEntity<DetailsUserBaseDTO> findById(@PathVariable String id) {
        var foundedUser = userService.findById(id);
        return ResponseEntity.ok(foundedUser);
    }

    @GetMapping("/me")
    @Operation(security = {@SecurityRequirement(name = SECURITY_HEADER_VALUE)})
    public ResponseEntity<DetailsOwnUserDTO> getDataCurrentLoggedUser() {
        var userData = userService.getCurrentUserData();
        return ResponseEntity.ok(userData);
    }

    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = SECURITY_HEADER_VALUE)})
    public ResponseEntity<UpdatedUserDTO> update(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO) {
        var user = userService.update(id, updateUserDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = SECURITY_HEADER_VALUE)})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/active")
    public ResponseEntity<Void> active(@RequestHeader String code) {
        userService.activate(code);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/resend-code")
    public ResponseEntity<Void> resendVerificationCode(@RequestBody LoginDTO loginDTO) {
        userService.resendActivationCode(loginDTO.email(), loginDTO.password());
        return ResponseEntity.accepted().build();
    }
}