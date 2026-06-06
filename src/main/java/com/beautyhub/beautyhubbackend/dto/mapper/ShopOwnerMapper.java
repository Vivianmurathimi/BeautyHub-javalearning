package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.dto.request.ShopOwnerRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.ShopOwnerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ShopOwnerMapper
        extends AbstractMapper
        <ShopOwner, ShopOwnerResponseDTO> {

    @Override
    public ShopOwnerResponseDTO toDTO(
            ShopOwner shopOwner) {
        ShopOwnerResponseDTO dto =
                new ShopOwnerResponseDTO();
        dto.setId(shopOwner.getId());
        dto.setShopName(shopOwner.getShopName());
        dto.setOwnerName(shopOwner.getOwnerName());
        dto.setAddress(shopOwner.getAddress());
        if (shopOwner.getCountry() != null) {
            dto.setCountryName(
                    shopOwner.getCountry().getName());
        }
        return dto;
    }

    @Override
    public ShopOwner toEntity(Object requestDTO) {
        ShopOwnerRequestDTO request =
                (ShopOwnerRequestDTO) requestDTO;
        ShopOwner shopOwner = new ShopOwner();
        shopOwner.setShopName(
                request.getShopName());
        shopOwner.setOwnerName(
                request.getOwnerName());
        shopOwner.setAddress(request.getAddress());
        return shopOwner;
    }
}