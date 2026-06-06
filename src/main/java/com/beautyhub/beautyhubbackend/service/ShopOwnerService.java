package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.AbstractRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
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
    protected AbstractRepository<ShopOwner>
    getRepository() {
        return shopOwnerRepository;
    }

    @Override
    public ShopOwner update(Long id,
                            ShopOwner updated) {
        ShopOwner existing = shopOwnerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ShopOwner not found: " + id));
        existing.setShopName(updated.getShopName());
        existing.setOwnerName(updated.getOwnerName());
        existing.setAddress(updated.getAddress());
        if (updated.getCountry() != null) {
            countryRepository.findById(
                            updated.getCountry().getId())
                    .ifPresent(existing::setCountry);
        }
        return shopOwnerRepository.save(existing);
    }
}