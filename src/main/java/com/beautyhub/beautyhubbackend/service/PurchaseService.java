package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Purchase;
import com.beautyhub.beautyhubbackend.repository.AbstractRepository;
import com.beautyhub.beautyhubbackend.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PurchaseService
        extends AbstractService<Purchase, Long> {

    private final PurchaseRepository
            purchaseRepository;

    public PurchaseService(
            PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    protected AbstractRepository<Purchase>
    getRepository() {
        return purchaseRepository;
    }

    // Extra methods
    public List<Purchase> findByShopOwnerId(
            Long shopOwnerId) {
        return purchaseRepository
                .findByShopOwnerId(shopOwnerId);
    }

    public List<Purchase> findByCompanyId(
            Long companyId) {
        return purchaseRepository
                .findByCompanyId(companyId);
    }

    // Override save to auto calculate total
    @Override
    public Purchase save(Purchase purchase) {
        purchase.setTotalPrice(
                purchase.getUnitPrice()
                        .multiply(BigDecimal.valueOf(
                                purchase.getQuantity())));
        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase update(Long id,
                           Purchase updated) {
        Purchase existing = purchaseRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found: " + id));
        existing.setQuantity(updated.getQuantity());
        existing.setUnitPrice(updated.getUnitPrice());
        existing.setTotalPrice(
                updated.getUnitPrice()
                        .multiply(BigDecimal.valueOf(
                                updated.getQuantity())));
        if (updated.getShopOwner() != null) {
            existing.setShopOwner(
                    updated.getShopOwner());
        }
        if (updated.getProduct() != null) {
            existing.setProduct(updated.getProduct());
        }
        if (updated.getCompany() != null) {
            existing.setCompany(updated.getCompany());
        }
        return purchaseRepository.save(existing);
    }
}