package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public abstract class AbstractApiController
        <T extends AbstractDomain, D> {

    protected abstract AbstractService<T, Long>
    getService();

    protected abstract AbstractMapper<T, D>
    getMapper();

    @GetMapping
    public ResponseEntity<List<D>> findAll() {
        return ResponseEntity.ok(
                getMapper().toDTOList(
                        getService().findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> findById(
            @PathVariable Long id) {
        return getService().findById(id)
                .map(getMapper()::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}