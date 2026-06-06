package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Purchase;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseRepository
        extends AbstractRepository<Purchase> {

    List<Purchase> findByShopOwnerId(
            Long shopOwnerId);

    List<Purchase> findByCompanyId(
            Long companyId);
}