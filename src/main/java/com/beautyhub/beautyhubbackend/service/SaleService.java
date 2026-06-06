package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.repository.AbstractRepository;
import com.beautyhub.beautyhubbackend.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class SaleService
        extends AbstractService<Sale, Long> {

    private final SaleRepository saleRepository;

    public SaleService(
            SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    protected AbstractRepository<Sale>
    getRepository() {
        return saleRepository;
    }

    // Extra methods
    public List<Sale> findByPersonId(
            Long personId) {
        return saleRepository
                .findByPersonId(personId);
    }

    public List<Sale> findByShopOwnerId(
            Long shopOwnerId) {
        return saleRepository
                .findByShopOwnerId(shopOwnerId);
    }

    public List<Sale> findByCompanyId(
            Long companyId) {
        return saleRepository
                .findByCompanyId(companyId);
    }

    // Override save — validate seller
    // and calculate total
    @Override
    public Sale save(Sale sale) {
        if (sale.getShopOwner() != null
                && sale.getCompany() != null) {
            throw new RuntimeException(
                    "Sale must have ShopOwner " +
                            "OR Company — not both");
        }
        if (sale.getShopOwner() == null
                && sale.getCompany() == null) {
            throw new RuntimeException(
                    "Sale must have either " +
                            "ShopOwner or Company");
        }
        sale.setTotalPrice(
                sale.getUnitPrice()
                        .multiply(BigDecimal.valueOf(
                                sale.getQuantity())));
        return saleRepository.save(sale);
    }

    @Override
    public Sale update(Long id, Sale updated) {
        Sale existing = saleRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sale not found: " + id));
        existing.setQuantity(updated.getQuantity());
        existing.setUnitPrice(updated.getUnitPrice());
        existing.setTotalPrice(
                updated.getUnitPrice()
                        .multiply(BigDecimal.valueOf(
                                updated.getQuantity())));
        if (updated.getPerson() != null) {
            existing.setPerson(updated.getPerson());
        }
        if (updated.getProduct() != null) {
            existing.setProduct(updated.getProduct());
        }
        if (updated.getShopOwner() != null) {
            existing.setShopOwner(
                    updated.getShopOwner());
            existing.setCompany(null);
        }
        if (updated.getCompany() != null) {
            existing.setCompany(updated.getCompany());
            existing.setShopOwner(null);
        }
        return saleRepository.save(existing);
    }
}