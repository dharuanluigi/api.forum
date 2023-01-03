package br.com.alura.api.forum.controller;

import br.com.alura.api.forum.dto.*;
import br.com.alura.api.forum.service.interfaces.IEmailService;
import br.com.alura.api.forum.service.interfaces.IUserService;
import br.com.alura.api.forum.util.Constants;
import br.com.alura.api.forum.wrapper.PageDetailsUserWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "User", description = "Manage what features the user can do")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IEmailService emailService;

    @PostMapping
    @Operation(summary = "Creates a user", description = "Endpoint to creates a user access into forum by unique email")
    @ApiResponse(responseCode = "409", description = "When a user was found already registered with the same username or email", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
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
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)}, summary = "Find all user with filter", description = "Possible see all usernames with their roles")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PageDetailsUserWrapper.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Page<DetailsUserDTO>> findAll(@PageableDefault Pageable pagination) {
        var allUsers = userService.findAll(pagination);
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)}, summary = "Find a user by their id", description = "See more details about a user")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DetailsUserBaseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<DetailsUserBaseDTO> findById(@PathVariable String id) {
        var foundedUser = userService.findById(id);
        return ResponseEntity.ok(foundedUser);
    }

    @GetMapping("/me")
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)}, summary = "Get current user data", description = "Possible see all data about your own logged user")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DetailsOwnUserDTO.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<DetailsOwnUserDTO> getDataCurrentLoggedUser() {
        var userData = userService.getCurrentUserData();
        return ResponseEntity.ok(userData);
    }

    @PutMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)}, summary = "Update your data", description = "Possible update just your own data, includes moderators role")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UpdatedUserDTO.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<UpdatedUserDTO> update(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO) {
        var user = userService.update(id, updateUserDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = Constants.SECURITY_HEADER_VALUE)}, summary = "Delete account", description = "Possible delete your own account and moderators can delete the other ones")
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/active")
    @Operation(summary = "Active account", description = "Used to active an account by informed code that was send in informed registered email")
    @ApiResponse(responseCode = "202", description = "Accepted")
    public ResponseEntity<Void> active(@RequestHeader String code) {
        userService.activate(code);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/resend-code")
    @Operation(summary = "Resend verification code", description = "If user has the your code expired or an old account should be activated again to receive new code activation")
    @ApiResponse(responseCode = "202", description = "Accepted")
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> resendVerificationCode(@RequestBody LoginDTO loginDTO) {
        userService.resendActivationCode(loginDTO.email(), loginDTO.password());
        return ResponseEntity.accepted().build();
    }
}