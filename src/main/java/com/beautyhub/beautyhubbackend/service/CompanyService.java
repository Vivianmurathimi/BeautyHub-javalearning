package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ClientService clientService;

    public CompanyService(CompanyRepository companyRepository,
                          ClientService clientService) {
        this.companyRepository = companyRepository;
        this.clientService = clientService;
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

    // UPDATE — Company only owns taxId
    public Company update(Long id, Company updatedCompany) {
        Company existing = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Company not found with id: " + id));
        existing.setTaxId(updatedCompany.getTaxId());
        return companyRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException(
                    "Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }
}