
package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends AbstractDomain {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    // ManyToOne → Company (supplier)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            nullable = false)
    private Company company;

    // ManyToMany → ShopOwner (inverse side)
    @ManyToMany(mappedBy = "inventory",
            fetch = FetchType.LAZY)
    private List<ShopOwner> shopOwners;
}