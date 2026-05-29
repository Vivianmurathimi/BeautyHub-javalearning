package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractService
        <T extends AbstractDomain,
                ID extends Long> {

    // Each service provides its own repository
    protected abstract JpaRepository<T, ID>
    getRepository();

    // Each service implements its own update
    // because fields are different
    public abstract T update(ID id, T entity);

    // ─── IDENTICAL IN ALL SERVICES ───────────

    // CREATE
    public T save(T entity) {
        return getRepository().save(entity);
    }

    // READ ALL
    public List<T> findAll() {
        return getRepository().findAll();
    }

    // READ ONE
    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    // DELETE
    public void deleteById(ID id) {
        if (!getRepository().existsById(id)) {
            throw new RuntimeException(
                    "Entity not found: " + id);
        }
        getRepository().deleteById(id);
    }
}