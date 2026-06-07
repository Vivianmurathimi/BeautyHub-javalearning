package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country extends AbstractDomain {

    @NotBlank(message = "Country name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Sign is required")
    @Size(max = 10,
            message = "Sign must be 10 characters or less")
    @Column(nullable = false, length = 10)
    private String sign;

    @OneToMany(mappedBy = "country",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ShopOwner> shopOwners;

    @OneToMany(mappedBy = "country",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Person> persons;
}