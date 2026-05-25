package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOwnerRepository
        extends JpaRepository<ShopOwner, Long> {
}