package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShopOwnerService {

    private final ShopOwnerRepository shopOwnerRepository;
    private final CountryRepository countryRepository;

    public ShopOwnerService(
            ShopOwnerRepository shopOwnerRepository,
            CountryRepository countryRepository) {
        this.shopOwnerRepository = shopOwnerRepository;
        this.countryRepository = countryRepository;
    }

    // CREATE
    public ShopOwner save(ShopOwner shopOwner) {
        return shopOwnerRepository.save(shopOwner);
    }

    // READ ALL
    public List<ShopOwner> findAll() {
        return shopOwnerRepository.findAll();
    }

    // READ ONE
    public Optional<ShopOwner> findById(Long id) {
        return shopOwnerRepository.findById(id);
    }

    // UPDATE
    public ShopOwner update(Long id,
                            ShopOwner updatedShopOwner) {
        ShopOwner existing = shopOwnerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ShopOwner not found: " + id));
        existing.setShopName(
                updatedShopOwner.getShopName());
        existing.setOwnerName(
                updatedShopOwner.getOwnerName());
        existing.setAddress(
                updatedShopOwner.getAddress());

        // Update country if provided
        if (updatedShopOwner.getCountry() != null) {
            Country country = countryRepository
                    .findById(updatedShopOwner
                            .getCountry().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Country not found"));
            existing.setCountry(country);
        }
        return shopOwnerRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!shopOwnerRepository.existsById(id)) {
            throw new RuntimeException(
                    "ShopOwner not found: " + id);
        }
        shopOwnerRepository.deleteById(id);
    }
}