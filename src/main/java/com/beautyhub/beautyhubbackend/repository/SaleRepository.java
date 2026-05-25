package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleRepository
        extends JpaRepository<Sale, Long> {


    List<Sale> findByPersonId(Long personId);


    List<Sale> findByShopOwnerId(Long shopOwnerId);


    List<Sale> findByCompanyId(Long companyId);
}