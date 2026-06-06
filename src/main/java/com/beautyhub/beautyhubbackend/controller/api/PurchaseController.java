package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Purchase;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.PurchaseMapper;
import com.beautyhub.beautyhubbackend.dto.request.PurchaseRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.PurchaseResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.ProductService;
import com.beautyhub.beautyhubbackend.service.PurchaseService;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController
        extends AbstractApiController
        <Purchase, PurchaseResponseDTO> {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;
    private final ShopOwnerService shopOwnerService;
    private final ProductService productService;
    private final CompanyService companyService;

    public PurchaseController(
            PurchaseService purchaseService,
            PurchaseMapper purchaseMapper,
            ShopOwnerService shopOwnerService,
            ProductService productService,
            CompanyService companyService) {
        this.purchaseService = purchaseService;
        this.purchaseMapper = purchaseMapper;
        this.shopOwnerService = shopOwnerService;
        this.productService = productService;
        this.companyService = companyService;
    }

    @Override
    protected AbstractService<Purchase, Long>
    getService() {
        return purchaseService;
    }

    @Override
    protected AbstractMapper<Purchase,
            PurchaseResponseDTO> getMapper() {
        return purchaseMapper;
    }

    @GetMapping("/shopowner/{shopOwnerId}")
    public ResponseEntity<List<PurchaseResponseDTO>>
    findByShopOwner(
            @PathVariable Long shopOwnerId) {
        return ResponseEntity.ok(
                purchaseMapper.toDTOList(
                        purchaseService
                                .findByShopOwnerId(
                                        shopOwnerId)));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<PurchaseResponseDTO>>
    findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                purchaseMapper.toDTOList(
                        purchaseService
                                .findByCompanyId(
                                        companyId)));
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> save(
            @Valid @RequestBody
            PurchaseRequestDTO requestDTO) {
        Purchase purchase = purchaseMapper
                .toEntity(requestDTO);
        shopOwnerService.findById(
                        requestDTO.getShopOwnerId())
                .ifPresent(purchase::setShopOwner);
        productService.findById(
                        requestDTO.getProductId())
                .ifPresent(purchase::setProduct);
        companyService.findById(
                        requestDTO.getCompanyId())
                .ifPresent(purchase::setCompany);
        return ResponseEntity.status(201)
                .body(purchaseMapper.toDTO(
                        purchaseService
                                .save(purchase)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            PurchaseRequestDTO requestDTO) {
        Purchase purchase = purchaseMapper
                .toEntity(requestDTO);
        shopOwnerService.findById(
                        requestDTO.getShopOwnerId())
                .ifPresent(purchase::setShopOwner);
        productService.findById(
                        requestDTO.getProductId())
                .ifPresent(purchase::setProduct);
        companyService.findById(
                        requestDTO.getCompanyId())
                .ifPresent(purchase::setCompany);
        return ResponseEntity.ok(
                purchaseMapper.toDTO(
                        purchaseService.update(
                                id, purchase)));
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