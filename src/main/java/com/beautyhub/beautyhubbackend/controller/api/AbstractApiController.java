package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractApiController
        <T extends AbstractDomain> {

    // Each controller provides its own service
    protected abstract AbstractService<T, Long>
    getService();

    // GET ALL
    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(
                getService().findAll());
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<T> findById(
            @PathVariable Long id) {
        return getService().findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // POST
    @PostMapping
    public ResponseEntity<T> save(
            @RequestBody T entity) {
        return ResponseEntity.status(201)
                .body(getService().save(entity));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<T> update(
            @PathVariable Long id,
            @RequestBody T entity) {
        return ResponseEntity.ok(
                getService().update(id, entity));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}