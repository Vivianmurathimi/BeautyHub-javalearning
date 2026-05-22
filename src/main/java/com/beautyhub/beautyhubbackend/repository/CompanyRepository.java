package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    // findAll()        ← FREE from JpaRepository
    // findById(id)     ← FREE from JpaRepository
    // save(country)    ← FREE from JpaRepository
    // deleteById(id)   ← FREE from JpaRepository
    // existsById(id)   ← FREE from JpaRepository

}

