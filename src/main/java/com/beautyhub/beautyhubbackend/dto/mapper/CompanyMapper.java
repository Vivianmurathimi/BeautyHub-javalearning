package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.dto.request.CompanyRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CompanyResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper
        extends AbstractMapper
        <Company, CompanyResponseDTO> {

    @Override
    public CompanyResponseDTO toDTO(
            Company company) {
        CompanyResponseDTO dto =
                new CompanyResponseDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setTaxId(company.getTaxId());
        dto.setAddress(company.getAddress());
        if (company.getCountry() != null) {
            dto.setCountryName(
                    company.getCountry().getName());
        }
        return dto;
    }

    @Override
    public Company toEntity(Object requestDTO) {
        CompanyRequestDTO request =
                (CompanyRequestDTO) requestDTO;
        Company company = new Company();
        company.setName(request.getName());
        company.setTaxId(request.getTaxId());
        company.setAddress(request.getAddress());
        return company;
    }
}