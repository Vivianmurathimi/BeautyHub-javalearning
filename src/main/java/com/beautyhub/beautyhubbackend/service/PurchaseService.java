package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.domain.Purchase;
import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.ProductRepository;
import com.beautyhub.beautyhubbackend.repository.PurchaseRepository;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ShopOwnerRepository shopOwnerRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            ShopOwnerRepository shopOwnerRepository,
            ProductRepository productRepository,
            CompanyRepository companyRepository) {
        this.purchaseRepository = purchaseRepository;
        this.shopOwnerRepository = shopOwnerRepository;
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    // CREATE
    public Purchase save(Purchase purchase) {
        // Auto calculate total price
        purchase.setTotalPrice(
                purchase.getUnitPrice().multiply(
                        BigDecimal.valueOf(
                                purchase.getQuantity())));
        return purchaseRepository.save(purchase);
    }

    // READ ALL
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    // READ ONE
    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    // READ BY SHOP OWNER
    public List<Purchase> findByShopOwnerId(
            Long shopOwnerId) {
        return purchaseRepository
                .findByShopOwnerId(shopOwnerId);
    }

    // READ BY COMPANY
    public List<Purchase> findByCompanyId(
            Long companyId) {
        return purchaseRepository
                .findByCompanyId(companyId);
    }

    // UPDATE
    public Purchase update(Long id,
                           Purchase updatedPurchase) {
        Purchase existing = purchaseRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found: " + id));
        existing.setQuantity(
                updatedPurchase.getQuantity());
        existing.setUnitPrice(
                updatedPurchase.getUnitPrice());

        // Recalculate total
        existing.setTotalPrice(
                updatedPurchase.getUnitPrice()
                        .multiply(BigDecimal.valueOf(
                                updatedPurchase.getQuantity())));

        // Update relations if provided
        if (updatedPurchase.getShopOwner() != null) {
            ShopOwner shopOwner = shopOwnerRepository
                    .findById(updatedPurchase
                            .getShopOwner().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "ShopOwner not found"));
            existing.setShopOwner(shopOwner);
        }
        if (updatedPurchase.getProduct() != null) {
            Product product = productRepository
                    .findById(updatedPurchase
                            .getProduct().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found"));
            existing.setProduct(product);
        }
        if (updatedPurchase.getCompany() != null) {
            Company company = companyRepository
                    .findById(updatedPurchase
                            .getCompany().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Company not found"));
            existing.setCompany(company);
        }
        return purchaseRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new RuntimeException(
                    "Purchase not found: " + id);
        }
        purchaseRepository.deleteById(id);
    }
}