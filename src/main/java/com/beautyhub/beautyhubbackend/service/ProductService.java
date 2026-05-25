package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductService(
            ProductRepository productRepository,
            CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    // CREATE
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // READ ALL
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // READ ONE
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // READ BY COMPANY
    public List<Product> findByCompanyId(
            Long companyId) {
        return productRepository
                .findByCompanyId(companyId);
    }

    // UPDATE
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

        // Update company if provided
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

    // DELETE
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException(
                    "Product not found: " + id);
        }
        productRepository.deleteById(id);
    }
}
