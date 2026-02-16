package com.jeferson.trajefino.controller;

import com.jeferson.trajefino.model.Product;
import com.jeferson.trajefino.model.dto.ProductDTO;
import com.jeferson.trajefino.model.dto.Message;
import com.jeferson.trajefino.service.ProductService;
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
@RequestMapping("/product")
@Tag(name = "Product", description = "API de gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar produtos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/active")
    @Operation(summary = "Listar produtos ativos", description = "Retorna uma lista com todos os produtos ativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos ativos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar produtos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<List<Product>> getActiveProducts() {
        return productService.findActiveProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<Product> getProductById(@PathVariable(required = true) Long id) {
        return productService.findProductById(id);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Buscar produtos por categoria", description = "Retorna uma lista de produtos de uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar produtos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return productService.findProductsByCategory(category);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos por nome", description = "Retorna produtos que contenham o nome informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar produtos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }

    @PostMapping
    @Operation(summary = "Criar novo produto", description = "Cadastra um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou código de barras já existente"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) throws Exception {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto completo", description = "Atualiza todos os dados de um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou código de barras já existente"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<Product> updateProduct(@PathVariable(required = true) Long id, @RequestBody @Valid ProductDTO productDTO) throws Exception {
        return productService.updateProduct(id, productDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente um produto", description = "Atualiza um ou mais atributos de um produto existente sem precisar enviar todos os campos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou código de barras já existente"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<Product> patchProduct(@PathVariable(required = true) Long id, @RequestBody ProductDTO productDTO) throws Exception {
        return productService.partialUpdateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema permanentemente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao deletar produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable(required = true) Long id) {
        return productService.deleteProduct(id);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desativar produto", description = "Marca um produto como inativo sem removê-lo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao desativar produto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)
                    )
            )
    })
    public ResponseEntity<Product> deactivateProduct(@PathVariable(required = true) Long id) {
        return productService.deactivateProduct(id);
    }
}
