package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.dto.request.CountryRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CountryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper
        extends AbstractMapper
        <Country, CountryResponseDTO> {

    @Override
    public CountryResponseDTO toDTO(
            Country country) {
        CountryResponseDTO dto =
                new CountryResponseDTO();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setSign(country.getSign());
        return dto;
    }

    @Override
    public Country toEntity(Object requestDTO) {
        CountryRequestDTO request =
                (CountryRequestDTO) requestDTO;
        Country country = new Country();
        country.setName(request.getName());
        country.setSign(request.getSign());
        return country;
    }
}