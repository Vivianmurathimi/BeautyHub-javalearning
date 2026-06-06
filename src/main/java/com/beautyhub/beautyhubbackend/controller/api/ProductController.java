package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.ProductMapper;
import com.beautyhub.beautyhubbackend.dto.request.ProductRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.ProductResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController
        extends AbstractApiController
        <Product, ProductResponseDTO> {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CompanyService companyService;

    public ProductController(
            ProductService productService,
            ProductMapper productMapper,
            CompanyService companyService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.companyService = companyService;
    }

    @Override
    protected AbstractService<Product, Long>
    getService() {
        return productService;
    }

    @Override
    protected AbstractMapper<Product,
            ProductResponseDTO> getMapper() {
        return productMapper;
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProductResponseDTO>>
    findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                productMapper.toDTOList(
                        productService
                                .findByCompanyId(
                                        companyId)));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(
            @Valid @RequestBody
            ProductRequestDTO requestDTO) {
        Product product = productMapper
                .toEntity(requestDTO);
        companyService.findById(
                        requestDTO.getCompanyId())
                .ifPresent(product::setCompany);
        return ResponseEntity.status(201)
                .body(productMapper.toDTO(
                        productService.save(product)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            ProductRequestDTO requestDTO) {
        Product product = productMapper
                .toEntity(requestDTO);
        companyService.findById(
                        requestDTO.getCompanyId())
                .ifPresent(product::setCompany);
        return ResponseEntity.ok(
                productMapper.toDTO(
                        productService.update(
                                id, product)));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}