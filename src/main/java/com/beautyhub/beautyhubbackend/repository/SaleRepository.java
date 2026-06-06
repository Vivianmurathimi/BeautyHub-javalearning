package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Sale;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleRepository
        extends AbstractRepository<Sale> {

    List<Sale> findByPersonId(Long personId);

    List<Sale> findByShopOwnerId(Long shopOwnerId);

    List<Sale> findByCompanyId(Long companyId);
}