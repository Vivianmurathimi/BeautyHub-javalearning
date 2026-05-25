package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shop_owner")
public class ShopOwner extends AbstractDomain {

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String address;

    // ManyToOne → Country
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id",
            nullable = false)
    private Country country;

    // ManyToMany → Product (inventory)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "shop_owner_product",
            joinColumns = @JoinColumn(
                    name = "shop_owner_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id")
    )
    private List<Product> inventory;

    // OneToMany → Sale
    @OneToMany(mappedBy = "shopOwner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Sale> sales;

    // OneToMany → Purchase
    @OneToMany(mappedBy = "shopOwner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Purchase> purchases;
}