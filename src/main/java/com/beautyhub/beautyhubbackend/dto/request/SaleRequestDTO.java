package com.beautyhub.beautyhubbackend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class SaleRequestDTO {

    @NotNull(message = "Person is required")
    private Long personId;

    @NotNull(message = "Product is required")
    private Long productId;

    // Nullable — sale can be from shop OR company
    private Long shopOwnerId;
    private Long companyId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1,
            message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;
}