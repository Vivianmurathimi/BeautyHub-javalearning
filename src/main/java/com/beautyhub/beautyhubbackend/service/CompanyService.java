package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyService
        extends AbstractService<Company, Long> {

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    public CompanyService(
            CompanyRepository companyRepository,
            CountryRepository countryRepository) {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    protected JpaRepository<Company, Long>
    getRepository() {
        return companyRepository;
    }

    @Override
    public Company update(Long id,
                          Company updatedCompany) {
        Company existing = companyRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found: " + id));
        existing.setName(updatedCompany.getName());
        existing.setTaxId(updatedCompany.getTaxId());
        existing.setAddress(
                updatedCompany.getAddress());
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
}