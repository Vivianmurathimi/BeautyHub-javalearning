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
@Table(name = "company")
public class Company extends AbstractDomain {

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Tax ID is required")
    @Column(nullable = false)
    private String taxId;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;


    @OneToOne
    @JoinColumn(name = "country_id",
            nullable = false,
            unique = true)
    private Country country;

    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Product> products;
}