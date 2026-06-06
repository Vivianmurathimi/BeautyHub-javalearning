package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository
        extends AbstractRepository<Product> {

    List<Product> findByCompanyId(Long companyId);
}