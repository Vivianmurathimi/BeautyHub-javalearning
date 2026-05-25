package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseRepository
        extends JpaRepository<Purchase, Long> {


    List<Purchase> findByShopOwnerId(Long shopOwnerId);


    List<Purchase> findByCompanyId(Long companyId);
}