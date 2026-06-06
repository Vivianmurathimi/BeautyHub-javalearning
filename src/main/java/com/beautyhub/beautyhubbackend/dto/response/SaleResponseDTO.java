package com.beautyhub.beautyhubbackend.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class SaleResponseDTO {

    private Long id;
    private String personName;
    private String productName;
    private String shopOwnerName;
    private String companyName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}