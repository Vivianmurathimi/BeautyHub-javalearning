package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country extends AbstractDomain {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String sign;

    // OneToMany → ShopOwner
    @OneToMany(mappedBy = "country",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ShopOwner> shopOwners;

    // OneToMany → Person
    @OneToMany(mappedBy = "country",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Person> persons;
}