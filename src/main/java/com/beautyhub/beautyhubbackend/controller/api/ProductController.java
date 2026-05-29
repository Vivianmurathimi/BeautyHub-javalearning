package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController
        extends AbstractApiController<Product> {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected AbstractService<Product, Long>
    getService() {
        return productService;
    }

    // Extra endpoint specific to Product
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Product>>
    findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                productService
                        .findByCompanyId(companyId));
    }
}