package com.beautyhub.beautyhubbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Personal ID is required")
    private String personalId;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Country is required")
    private Long countryId;
}