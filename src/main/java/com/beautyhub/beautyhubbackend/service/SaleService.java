
package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.PersonRepository;
import com.beautyhub.beautyhubbackend.repository.ProductRepository;
import com.beautyhub.beautyhubbackend.repository.SaleRepository;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final PersonRepository personRepository;
    private final ProductRepository productRepository;
    private final ShopOwnerRepository shopOwnerRepository;
    private final CompanyRepository companyRepository;

    public SaleService(
            SaleRepository saleRepository,
            PersonRepository personRepository,
            ProductRepository productRepository,
            ShopOwnerRepository shopOwnerRepository,
            CompanyRepository companyRepository) {
        this.saleRepository = saleRepository;
        this.personRepository = personRepository;
        this.productRepository = productRepository;
        this.shopOwnerRepository = shopOwnerRepository;
        this.companyRepository = companyRepository;
    }

    // CREATE
    public Sale save(Sale sale) {
        // Validate seller — must be
        // ShopOwner OR Company, not both
        if (sale.getShopOwner() != null
                && sale.getCompany() != null) {
            throw new RuntimeException(
                    "Sale must have either a ShopOwner " +
                            "or a Company as seller, not both");
        }
        if (sale.getShopOwner() == null
                && sale.getCompany() == null) {
            throw new RuntimeException(
                    "Sale must have either a ShopOwner " +
                            "or a Company as seller");
        }

        // Auto calculate total price
        sale.setTotalPrice(
                sale.getUnitPrice().multiply(
                        BigDecimal.valueOf(
                                sale.getQuantity())));
        return saleRepository.save(sale);
    }

    // READ ALL
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    // READ ONE
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    // READ BY PERSON
    public List<Sale> findByPersonId(Long personId) {
        return saleRepository.findByPersonId(personId);
    }

    // READ BY SHOP OWNER
    public List<Sale> findByShopOwnerId(
            Long shopOwnerId) {
        return saleRepository
                .findByShopOwnerId(shopOwnerId);
    }

    // READ BY COMPANY
    public List<Sale> findByCompanyId(Long companyId) {
        return saleRepository
                .findByCompanyId(companyId);
    }

    // UPDATE
    public Sale update(Long id, Sale updatedSale) {
        Sale existing = saleRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sale not found: " + id));
        existing.setQuantity(
                updatedSale.getQuantity());
        existing.setUnitPrice(
                updatedSale.getUnitPrice());

        // Recalculate total
        existing.setTotalPrice(
                updatedSale.getUnitPrice()
                        .multiply(BigDecimal.valueOf(
                                updatedSale.getQuantity())));

        // Update relations if provided
        if (updatedSale.getPerson() != null) {
            Person person = personRepository
                    .findById(updatedSale
                            .getPerson().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Person not found"));
            existing.setPerson(person);
        }
        if (updatedSale.getProduct() != null) {
            Product product = productRepository
                    .findById(updatedSale
                            .getProduct().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"));
            existing.setProduct(product);
        }
        if (updatedSale.getShopOwner() != null) {
            ShopOwner shopOwner = shopOwnerRepository
                    .findById(updatedSale
                            .getShopOwner().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "ShopOwner not found"));
            existing.setShopOwner(shopOwner);
            existing.setCompany(null);
        }
        if (updatedSale.getCompany() != null) {
            Company company = companyRepository
                    .findById(updatedSale
                            .getCompany().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Company not found"));
            existing.setCompany(company);
            existing.setShopOwner(null);
        }
        return saleRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new RuntimeException(
                    "Sale not found: " + id);
        }
        saleRepository.deleteById(id);
    }
}