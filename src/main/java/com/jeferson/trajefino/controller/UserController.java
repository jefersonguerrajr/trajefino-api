package com.jeferson.trajefino.controller;

import com.jeferson.trajefino.model.dto.Message;
import com.jeferson.trajefino.model.User;
import com.jeferson.trajefino.model.dto.UserDTO;
import com.jeferson.trajefino.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "API de gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas ADMIN e OPERATOR"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar usuários",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<List<User>> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema e retorna o usuário criado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou username já existente"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas ADMIN"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar usuário",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO user) throws Exception {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar usuário completo", description = "Atualiza todos os dados de um usuário existente e retorna o usuário atualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID inválido ou dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas ADMIN"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar usuário",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<User> editUser(@PathVariable(required = true) Long id, @RequestBody @Valid UserDTO user) throws Exception {
        user.setId(id);
        return userService.saveUser(user);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar parcialmente um usuário", description = "Atualiza um ou mais atributos de um usuário existente sem precisar enviar todos os campos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID inválido ou dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas ADMIN"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar usuário",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<User> patchUser(@PathVariable(required = true) Long id, @RequestBody UserDTO user) throws Exception {
        return userService.partialUpdateUser(id, user);
    }

}
