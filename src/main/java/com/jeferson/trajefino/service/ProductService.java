package com.jeferson.trajefino.service;

import com.jeferson.trajefino.exception.ResourceNotFoundException;
import com.jeferson.trajefino.model.Product;
import com.jeferson.trajefino.model.dto.ProductDTO;
import com.jeferson.trajefino.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<List<Product>> findActiveProducts() {
        return ResponseEntity.ok(productRepository.findByActiveTrue());
    }

    public ResponseEntity<Product> findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<List<Product>> findProductsByCategory(String category) {
        return ResponseEntity.ok(productRepository.findByCategory(category));
    }

    public ResponseEntity<List<Product>> searchProductsByName(String name) {
        return ResponseEntity.ok(productRepository.findByNameContainingIgnoreCase(name));
    }

    @Transactional
    public ResponseEntity<Product> createProduct(ProductDTO productDTO) throws Exception {
        validateProductDTO(productDTO, true);

        if (productDTO.getBarcode() != null && !productDTO.getBarcode().trim().isEmpty()) {
            if (productRepository.existsByBarcode(productDTO.getBarcode())) {
                throw new Exception("Código de barras já está em uso: " + productDTO.getBarcode());
            }
        }

        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock() != null ? productDTO.getStock() : 0)
                .category(productDTO.getCategory())
                .brand(productDTO.getBrand())
                .barcode(productDTO.getBarcode())
                .active(productDTO.getActive() != null ? productDTO.getActive() : true)
                .build();

        return ResponseEntity.ok(productRepository.save(product));
    }

    @Transactional
    public ResponseEntity<Product> updateProduct(Long id, ProductDTO productDTO) throws Exception {
        validateProductDTO(productDTO, true);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        if (productDTO.getBarcode() != null && !productDTO.getBarcode().trim().isEmpty()) {
            if (!productDTO.getBarcode().equals(product.getBarcode()) &&
                productRepository.existsByBarcode(productDTO.getBarcode())) {
                throw new Exception("Código de barras já está em uso: " + productDTO.getBarcode());
            }
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());
        product.setBrand(productDTO.getBrand());
        product.setBarcode(productDTO.getBarcode());
        product.setActive(productDTO.getActive() != null ? productDTO.getActive() : true);

        return ResponseEntity.ok(productRepository.save(product));
    }

    @Transactional
    public ResponseEntity<Product> partialUpdateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        // Atualiza apenas os campos que foram fornecidos (não nulos)
        if (productDTO.getName() != null && !productDTO.getName().trim().isEmpty()) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            if (productDTO.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new Exception("Preço deve ser maior que zero");
            }
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getStock() != null) {
            if (productDTO.getStock() < 0) {
                throw new Exception("Estoque não pode ser negativo");
            }
            product.setStock(productDTO.getStock());
        }
        if (productDTO.getCategory() != null && !productDTO.getCategory().trim().isEmpty()) {
            product.setCategory(productDTO.getCategory());
        }
        if (productDTO.getBrand() != null && !productDTO.getBrand().trim().isEmpty()) {
            product.setBrand(productDTO.getBrand());
        }
        if (productDTO.getBarcode() != null && !productDTO.getBarcode().trim().isEmpty()) {
            if (!productDTO.getBarcode().equals(product.getBarcode()) &&
                productRepository.existsByBarcode(productDTO.getBarcode())) {
                throw new Exception("Código de barras já está em uso: " + productDTO.getBarcode());
            }
            product.setBarcode(productDTO.getBarcode());
        }
        if (productDTO.getActive() != null) {
            product.setActive(productDTO.getActive());
        }

        return ResponseEntity.ok(productRepository.save(product));
    }

    @Transactional
    public ResponseEntity<Void> deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Product> deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        product.setActive(false);
        return ResponseEntity.ok(productRepository.save(product));
    }

    private void validateProductDTO(ProductDTO productDTO, boolean isRequired) throws Exception {
        if (isRequired) {
            if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
                throw new Exception("Nome do produto é obrigatório");
            }
            if (productDTO.getPrice() == null || productDTO.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new Exception("Preço deve ser maior que zero");
            }
        }
    }
}
