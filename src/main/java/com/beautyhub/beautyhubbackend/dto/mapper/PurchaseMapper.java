package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Purchase;
import com.beautyhub.beautyhubbackend.dto.request.PurchaseRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.PurchaseResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper
        extends AbstractMapper
        <Purchase, PurchaseResponseDTO> {

    @Override
    public PurchaseResponseDTO toDTO(
            Purchase purchase) {
        PurchaseResponseDTO dto =
                new PurchaseResponseDTO();
        dto.setId(purchase.getId());
        dto.setQuantity(purchase.getQuantity());
        dto.setUnitPrice(purchase.getUnitPrice());
        dto.setTotalPrice(purchase.getTotalPrice());
        if (purchase.getShopOwner() != null) {
            dto.setShopOwnerName(
                    purchase.getShopOwner()
                            .getShopName());
        }
        if (purchase.getProduct() != null) {
            dto.setProductName(
                    purchase.getProduct().getName());
        }
        if (purchase.getCompany() != null) {
            dto.setCompanyName(
                    purchase.getCompany().getName());
        }
        return dto;
    }

    @Override
    public Purchase toEntity(Object requestDTO) {
        PurchaseRequestDTO request =
                (PurchaseRequestDTO) requestDTO;
        Purchase purchase = new Purchase();
        purchase.setQuantity(request.getQuantity());
        purchase.setUnitPrice(
                request.getUnitPrice());
        return purchase;
    }
}