package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shopowners")
public class ShopOwnerController {

    private final ShopOwnerService shopOwnerService;

    public ShopOwnerController(
            ShopOwnerService shopOwnerService) {
        this.shopOwnerService = shopOwnerService;
    }

    // GET ALL → /api/shopowners
    @GetMapping
    public ResponseEntity<List<ShopOwner>> findAll() {
        return ResponseEntity.ok(
                shopOwnerService.findAll());
    }

    // GET ONE → /api/shopowners/1
    @GetMapping("/{id}")
    public ResponseEntity<ShopOwner> findById(
            @PathVariable Long id) {
        return shopOwnerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // POST → /api/shopowners
    @PostMapping
    public ResponseEntity<ShopOwner> save(
            @RequestBody ShopOwner shopOwner) {
        return ResponseEntity.status(201)
                .body(shopOwnerService.save(shopOwner));
    }

    // PUT → /api/shopowners/1
    @PutMapping("/{id}")
    public ResponseEntity<ShopOwner> update(
            @PathVariable Long id,
            @RequestBody ShopOwner shopOwner) {
        return ResponseEntity.ok(
                shopOwnerService.update(id, shopOwner));
    }

    // DELETE → /api/shopowners/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        shopOwnerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}