package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "purchase")
public class Purchase extends AbstractDomain {

    // ManyToOne → ShopOwner (who is buying)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_owner_id",
            nullable = false)
    private ShopOwner shopOwner;

    // ManyToOne → Product (what is being bought)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            nullable = false)
    private Product product;

    // ManyToOne → Company (who is selling)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            nullable = false)
    private Company company;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal totalPrice;
}