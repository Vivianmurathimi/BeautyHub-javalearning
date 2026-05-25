package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Purchase;
import com.beautyhub.beautyhubbackend.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(
            PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    // GET ALL → /api/purchases
    @GetMapping
    public ResponseEntity<List<Purchase>> findAll() {
        return ResponseEntity.ok(
                purchaseService.findAll());
    }

    // GET ONE → /api/purchases/1
    @GetMapping("/{id}")
    public ResponseEntity<Purchase> findById(
            @PathVariable Long id) {
        return purchaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // GET BY SHOP OWNER → /api/purchases/shopowner/1
    @GetMapping("/shopowner/{shopOwnerId}")
    public ResponseEntity<List<Purchase>> findByShopOwner(
            @PathVariable Long shopOwnerId) {
        return ResponseEntity.ok(
                purchaseService
                        .findByShopOwnerId(shopOwnerId));
    }

    // GET BY COMPANY → /api/purchases/company/1
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Purchase>> findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                purchaseService
                        .findByCompanyId(companyId));
    }

    // POST → /api/purchases
    @PostMapping
    public ResponseEntity<Purchase> save(
            @RequestBody Purchase purchase) {
        return ResponseEntity.status(201)
                .body(purchaseService.save(purchase));
    }

    // PUT → /api/purchases/1
    @PutMapping("/{id}")
    public ResponseEntity<Purchase> update(
            @PathVariable Long id,
            @RequestBody Purchase purchase) {
        return ResponseEntity.ok(
                purchaseService.update(id, purchase));
    }

    // DELETE → /api/purchases/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        purchaseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}