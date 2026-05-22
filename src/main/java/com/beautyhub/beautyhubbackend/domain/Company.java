package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends Client {

    @Column(nullable = false)
    private String TaxId;
}
