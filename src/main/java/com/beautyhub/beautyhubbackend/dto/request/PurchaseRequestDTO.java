package com.beautyhub.beautyhubbackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseRequestDTO {

    @NotNull(message = "Shop owner is required")
    private Long shopOwnerId;

    @NotNull(message = "Product is required")
    private Long productId;

    @NotNull(message = "Company is required")
    private Long companyId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1,
            message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;
}