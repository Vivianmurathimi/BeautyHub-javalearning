package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.repository.AbstractRepository;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
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
    protected AbstractRepository<Company>
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
            countryRepository.findById(
                            updatedCompany
                                    .getCountry().getId())
                    .ifPresent(existing::setCountry);
        }
        return companyRepository.save(existing);
    }
}