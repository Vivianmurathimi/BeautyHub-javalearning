package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "sale")
public class Sale extends AbstractDomain {

    // ManyToOne → Person (always the buyer)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",
            nullable = false)
    private Person person;

    // ManyToOne → Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            nullable = false)
    private Product product;

    // Seller is EITHER ShopOwner OR Company
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_owner_id")
    private ShopOwner shopOwner; // nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company; // nullable

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal totalPrice;
}