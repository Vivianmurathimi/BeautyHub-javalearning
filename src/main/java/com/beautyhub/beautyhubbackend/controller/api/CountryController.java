package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.CountryMapper;
import com.beautyhub.beautyhubbackend.dto.request.CountryRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CountryResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController
        extends AbstractApiController
        <Country, CountryResponseDTO> {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    public CountryController(
            CountryService countryService,
            CountryMapper countryMapper) {
        this.countryService = countryService;
        this.countryMapper = countryMapper;
    }

    @Override
    protected AbstractService<Country, Long>
    getService() {
        return countryService;
    }

    @Override
    protected AbstractMapper<Country,
            CountryResponseDTO> getMapper() {
        return countryMapper;
    }

    @PostMapping
    public ResponseEntity<CountryResponseDTO> save(
            @Valid @RequestBody
            CountryRequestDTO requestDTO) {
        Country country = countryMapper
                .toEntity(requestDTO);
        return ResponseEntity.status(201)
                .body(countryMapper.toDTO(
                        countryService.save(country)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            CountryRequestDTO requestDTO) {
        Country country = countryMapper
                .toEntity(requestDTO);
        return ResponseEntity.ok(
                countryMapper.toDTO(
                        countryService.update(
                                id, country)));
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