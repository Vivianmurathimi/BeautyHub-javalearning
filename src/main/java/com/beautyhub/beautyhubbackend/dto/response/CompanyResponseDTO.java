package com.beautyhub.beautyhubbackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDTO {

    private Long id;
    private String name;
    private String taxId;
    private String address;
    private String countryName;
}
