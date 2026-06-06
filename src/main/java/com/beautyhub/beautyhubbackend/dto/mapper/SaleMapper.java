package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.dto.request.SaleRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.SaleResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper
        extends AbstractMapper
        <Sale, SaleResponseDTO> {

    @Override
    public SaleResponseDTO toDTO(Sale sale) {
        SaleResponseDTO dto = new SaleResponseDTO();
        dto.setId(sale.getId());
        dto.setQuantity(sale.getQuantity());
        dto.setUnitPrice(sale.getUnitPrice());
        dto.setTotalPrice(sale.getTotalPrice());
        if (sale.getPerson() != null) {
            dto.setPersonName(
                    sale.getPerson().getName());
        }
        if (sale.getProduct() != null) {
            dto.setProductName(
                    sale.getProduct().getName());
        }
        if (sale.getShopOwner() != null) {
            dto.setShopOwnerName(
                    sale.getShopOwner()
                            .getShopName());
        }
        if (sale.getCompany() != null) {
            dto.setCompanyName(
                    sale.getCompany().getName());
        }
        return dto;
    }

    @Override
    public Sale toEntity(Object requestDTO) {
        SaleRequestDTO request =
                (SaleRequestDTO) requestDTO;
        Sale sale = new Sale();
        sale.setQuantity(request.getQuantity());
        sale.setUnitPrice(request.getUnitPrice());
        return sale;
    }
}