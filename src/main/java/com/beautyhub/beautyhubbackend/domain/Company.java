package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends AbstractDomain {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String taxId;

    @Column(nullable = false)
    private String address;

    // OneToOne → Country
    // One company is HQ'd in exactly one country
    @OneToOne
    @JoinColumn(name = "country_id",
            nullable = false,
            unique = true)
    private Country country;

    // OneToMany → Product
    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Product> products;
}