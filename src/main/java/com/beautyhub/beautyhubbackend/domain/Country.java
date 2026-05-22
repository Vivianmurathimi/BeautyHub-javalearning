package com.beautyhub.beautyhubbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    @Entity
    @Table(name = "country")
    public class Country extends AbstractDomain {

        @Column(nullable = false)
        private String name;

        @Column(nullable = false, length = 10)
        private String sign;
    }

