package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ShopOwnerService shopOwnerService;

    public ClientController(ShopOwnerService shopOwnerService) {
        this.shopOwnerService = shopOwnerService;
    }

    // GET ALL — /api/clients
    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(shopOwnerService.findAll());
    }

    // GET ONE — /api/clients/1
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(
            @PathVariable Long id) {
        return shopOwnerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST — /api/clients
    @PostMapping
    public ResponseEntity<Client> save(
            @RequestBody Client client) {
        return ResponseEntity.status(201)
                .body(shopOwnerService.save(client));
    }

    // PUT — /api/clients/1
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(
            @PathVariable Long id,
            @RequestBody Client client) {
        return ResponseEntity.ok(
                shopOwnerService.update(id, client));
    }

    // DELETE — /api/clients/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        shopOwnerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}