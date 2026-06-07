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
@Table(name = "person")
public class Person extends AbstractDomain {

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Personal ID is required")
    @Column(nullable = false)
    private String personalId;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id",
            nullable = false)
    private Country country;

    @OneToMany(mappedBy = "person",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Sale> purchases;
}