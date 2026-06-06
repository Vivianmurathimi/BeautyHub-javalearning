package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.CompanyMapper;
import com.beautyhub.beautyhubbackend.dto.request.CompanyRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CompanyResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController
        extends AbstractApiController
        <Company, CompanyResponseDTO> {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final CountryService countryService;

    public CompanyController(
            CompanyService companyService,
            CompanyMapper companyMapper,
            CountryService countryService) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.countryService = countryService;
    }

    @Override
    protected AbstractService<Company, Long>
    getService() {
        return companyService;
    }

    @Override
    protected AbstractMapper<Company,
            CompanyResponseDTO> getMapper() {
        return companyMapper;
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> save(
            @Valid @RequestBody
            CompanyRequestDTO requestDTO) {
        Company company = companyMapper
                .toEntity(requestDTO);
        countryService.findById(
                        requestDTO.getCountryId())
                .ifPresent(company::setCountry);
        return ResponseEntity.status(201)
                .body(companyMapper.toDTO(
                        companyService.save(company)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            CompanyRequestDTO requestDTO) {
        Company company = companyMapper
                .toEntity(requestDTO);
        countryService.findById(
                        requestDTO.getCountryId())
                .ifPresent(company::setCountry);
        return ResponseEntity.ok(
                companyMapper.toDTO(
                        companyService.update(
                                id, company)));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}