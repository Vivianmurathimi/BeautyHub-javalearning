package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService
        extends AbstractService<Product, Long> {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductService(
            ProductRepository productRepository,
            CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    protected JpaRepository<Product, Long>
    getRepository() {
        return productRepository;
    }

    // Extra method specific to Product
    public List<Product> findByCompanyId(
            Long companyId) {
        return productRepository
                .findByCompanyId(companyId);
    }

    @Override
    public Product update(Long id,
                          Product updatedProduct) {
        Product existing = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found: " + id));
        existing.setName(updatedProduct.getName());
        existing.setDescription(
                updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getCompany() != null) {
            Company company = companyRepository
                    .findById(updatedProduct
                            .getCompany().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Company not found"));
            existing.setCompany(company);
        }
        return productRepository.save(existing);
    }
}