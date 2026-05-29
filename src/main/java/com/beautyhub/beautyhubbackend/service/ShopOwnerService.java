package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopOwnerService
        extends AbstractService<ShopOwner, Long> {

    private final ShopOwnerRepository shopOwnerRepository;
    private final CountryRepository countryRepository;

    public ShopOwnerService(
            ShopOwnerRepository shopOwnerRepository,
            CountryRepository countryRepository) {
        this.shopOwnerRepository = shopOwnerRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    protected JpaRepository<ShopOwner, Long>
    getRepository() {
        return shopOwnerRepository;
    }

    @Override
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
}