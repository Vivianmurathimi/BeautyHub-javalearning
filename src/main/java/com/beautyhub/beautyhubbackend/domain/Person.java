package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person extends AbstractDomain {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String personalId;

    @Column(nullable = false)
    private String address;

    // ManyToOne → Country
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id",
            nullable = false)
    private Country country;

    // OneToMany → Sale (as buyer)
    @OneToMany(mappedBy = "person",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Sale> purchases;
}