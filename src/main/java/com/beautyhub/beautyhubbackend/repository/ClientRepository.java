package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Country, Long> {

    // findAll()        ← FREE from JpaRepository
    // findById(id)     ← FREE from JpaRepository
    // save(country)    ← FREE from JpaRepository
    // deleteById(id)   ← FREE from JpaRepository
    // existsById(id)   ← FREE from JpaRepository

}
