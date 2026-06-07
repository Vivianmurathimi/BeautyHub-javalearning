package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shop_owner")
public class ShopOwner extends AbstractDomain {

    @NotBlank(message = "Shop name is required")
    @Column(nullable = false)
    private String shopName;

    @NotBlank(message = "Owner name is required")
    @Column(nullable = false)
    private String ownerName;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id",
            nullable = false)
    private Country country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "shop_owner_product",
            joinColumns = @JoinColumn(
                    name = "shop_owner_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id")
    )
    private List<Product> inventory;

    @OneToMany(mappedBy = "shopOwner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Sale> sales;

    @OneToMany(mappedBy = "shopOwner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Purchase> purchases;
}