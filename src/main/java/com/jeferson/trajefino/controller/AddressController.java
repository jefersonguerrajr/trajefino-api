package com.jeferson.trajefino.controller;

import com.jeferson.trajefino.model.Address;
import com.jeferson.trajefino.model.AddressDTO;
import com.jeferson.trajefino.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Tag(name = "Address", description = "API de gerenciamento de endereços")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar endereços do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<List<Address>> getAddressesByUser(@PathVariable Long userId) {
        return addressService.findAddressesByUserId(userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return addressService.findAddressById(id);
    }

    @PostMapping
    @Operation(summary = "Criar novo endereço")
    public ResponseEntity<Address> createAddress(@RequestBody @Valid AddressDTO addressDTO) throws Exception {
        return addressService.createAddress(addressDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço completo")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody @Valid AddressDTO dto) throws Exception {
        return addressService.updateAddress(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um endereço")
    public ResponseEntity<Address> patchAddress(@PathVariable Long id, @RequestBody AddressDTO dto) throws Exception {
        return addressService.partialUpdateAddress(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar endereço")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }

    @PatchMapping("/{id}/set-default")
    @Operation(summary = "Definir endereço como padrão")
    public ResponseEntity<Address> setDefaultAddress(@PathVariable Long id) {
        return addressService.setDefaultAddress(id);
    }
}
