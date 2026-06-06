package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.ShopOwnerMapper;
import com.beautyhub.beautyhubbackend.dto.request.ShopOwnerRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.ShopOwnerResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopowners")
public class ShopOwnerController
        extends AbstractApiController
        <ShopOwner, ShopOwnerResponseDTO> {

    private final ShopOwnerService shopOwnerService;
    private final ShopOwnerMapper shopOwnerMapper;
    private final CountryService countryService;

    public ShopOwnerController(
            ShopOwnerService shopOwnerService,
            ShopOwnerMapper shopOwnerMapper,
            CountryService countryService) {
        this.shopOwnerService = shopOwnerService;
        this.shopOwnerMapper = shopOwnerMapper;
        this.countryService = countryService;
    }

    @Override
    protected AbstractService<ShopOwner, Long>
    getService() {
        return shopOwnerService;
    }

    @Override
    protected AbstractMapper<ShopOwner,
            ShopOwnerResponseDTO> getMapper() {
        return shopOwnerMapper;
    }

    @PostMapping
    public ResponseEntity<ShopOwnerResponseDTO> save(
            @Valid @RequestBody
            ShopOwnerRequestDTO requestDTO) {
        ShopOwner shopOwner = shopOwnerMapper
                .toEntity(requestDTO);
        countryService.findById(
                        requestDTO.getCountryId())
                .ifPresent(shopOwner::setCountry);
        return ResponseEntity.status(201)
                .body(shopOwnerMapper.toDTO(
                        shopOwnerService
                                .save(shopOwner)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopOwnerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            ShopOwnerRequestDTO requestDTO) {
        ShopOwner shopOwner = shopOwnerMapper
                .toEntity(requestDTO);
        countryService.findById(
                        requestDTO.getCountryId())
                .ifPresent(shopOwner::setCountry);
        return ResponseEntity.ok(
                shopOwnerMapper.toDTO(
                        shopOwnerService.update(
                                id, shopOwner)));
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