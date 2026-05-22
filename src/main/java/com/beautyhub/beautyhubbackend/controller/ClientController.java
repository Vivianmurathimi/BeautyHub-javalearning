package com.beautyhub.beautyhubbackend.controller;

import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // GET ALL — /api/clients
    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    // GET ONE — /api/clients/1
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(
            @PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST — /api/clients
    @PostMapping
    public ResponseEntity<Client> save(
            @RequestBody Client client) {
        return ResponseEntity.status(201)
                .body(clientService.save(client));
    }

    // PUT — /api/clients/1
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(
            @PathVariable Long id,
            @RequestBody Client client) {
        return ResponseEntity.ok(
                clientService.update(id, client));
    }

    // DELETE — /api/clients/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}