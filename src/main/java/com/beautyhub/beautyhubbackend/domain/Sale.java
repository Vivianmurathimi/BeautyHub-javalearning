package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "sale")
public class Sale extends AbstractDomain {

    @NotNull(message = "Person is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",
            nullable = false)
    private Person person;

    @NotNull(message = "Product is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            nullable = false)
    private Product product;

    // Nullable — either ShopOwner OR Company
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_owner_id")
    private ShopOwner shopOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @NotNull(message = "Quantity is required")
    @Min(value = 1,
            message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal totalPrice;
}