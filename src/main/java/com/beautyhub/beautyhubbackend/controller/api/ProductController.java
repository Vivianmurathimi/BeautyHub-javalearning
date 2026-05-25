package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {
        this.productService = productService;
    }

    // GET ALL → /api/products
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(
                productService.findAll());
    }

    // GET ONE → /api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(
            @PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // GET BY COMPANY → /api/products/company/1
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Product>> findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                productService
                        .findByCompanyId(companyId));
    }

    // POST → /api/products
    @PostMapping
    public ResponseEntity<Product> save(
            @RequestBody Product product) {
        return ResponseEntity.status(201)
                .body(productService.save(product));
    }

    // PUT → /api/products/1
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestBody Product product) {
        return ResponseEntity.ok(
                productService.update(id, product));
    }

    // DELETE → /api/products/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}