package com.jeferson.trajefino.controller;

import com.jeferson.trajefino.model.Message;
import com.jeferson.trajefino.model.User;
import com.jeferson.trajefino.model.UserDTO;
import com.jeferson.trajefino.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar usuários")
    })
    public ResponseEntity<List<User>> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    @Operation(summary = "Salvar um novo usuario", description = "Salva e retorna o usuário com um id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao salvar usuário",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO user) throws Exception {
        return userService.saveUser(user);
    }

}
