package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends AbstractDomain {

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0",
            message = "Price must be positive")
    @Column(nullable = false)
    private BigDecimal price;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id",
            nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "inventory",
            fetch = FetchType.LAZY)
    private List<ShopOwner> shopOwners;
}