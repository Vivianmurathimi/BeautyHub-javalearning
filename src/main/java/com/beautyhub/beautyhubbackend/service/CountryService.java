package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.repository.AbstractRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryService
        extends AbstractService<Country, Long> {

    private final CountryRepository countryRepository;

    public CountryService(
            CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    protected AbstractRepository<Country>
    getRepository() {
        return countryRepository;
    }

    @Override
    public Country update(Long id,
                          Country updatedCountry) {
        Country existing = countryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Country not found: " + id));
        existing.setName(updatedCountry.getName());
        existing.setSign(updatedCountry.getSign());
        return countryRepository.save(existing);
    }
}