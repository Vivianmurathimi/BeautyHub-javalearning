package com.beautyhub.beautyhubbackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponseDTO {

    private Long id;
    private String name;
    private String personalId;
    private String address;
    private String countryName;
}