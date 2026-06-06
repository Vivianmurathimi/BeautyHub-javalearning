package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.dto.request.ProductRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper
        extends AbstractMapper
        <Product, ProductResponseDTO> {

    @Override
    public ProductResponseDTO toDTO(
            Product product) {
        ProductResponseDTO dto =
                new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(
                product.getDescription());
        dto.setPrice(product.getPrice());
        if (product.getCompany() != null) {
            dto.setCompanyName(
                    product.getCompany().getName());
        }
        return dto;
    }

    @Override
    public Product toEntity(Object requestDTO) {
        ProductRequestDTO request =
                (ProductRequestDTO) requestDTO;
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(
                request.getDescription());
        product.setPrice(request.getPrice());
        return product;
    }
}