package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    public CompanyService(
            CompanyRepository companyRepository,
            CountryRepository countryRepository) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
    }

    // CREATE
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    // READ ALL
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    // READ ONE
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    // UPDATE
    public Company update(Long id,
                          Company updatedCompany) {
        Company existing = companyRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found: " + id));
        existing.setName(updatedCompany.getName());
        existing.setTaxId(updatedCompany.getTaxId());
        existing.setAddress(updatedCompany.getAddress());

        // Update country if provided
        if (updatedCompany.getCountry() != null) {
            Country country = countryRepository
                    .findById(updatedCompany
                            .getCountry().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Country not found"));
            existing.setCountry(country);
        }
        return companyRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException(
                    "Company not found: " + id);
        }
        companyRepository.deleteById(id);
    }
}