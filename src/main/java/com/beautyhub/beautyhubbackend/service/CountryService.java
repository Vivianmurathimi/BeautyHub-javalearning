package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(
            CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // CREATE
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    // READ ALL
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    // READ ONE
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    // UPDATE
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

    // DELETE
    public void deleteById(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException(
                    "Country not found: " + id);
        }
        countryRepository.deleteById(id);
    }
}