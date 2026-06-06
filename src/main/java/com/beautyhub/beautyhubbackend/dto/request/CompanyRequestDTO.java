package com.beautyhub.beautyhubbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDTO {

    @NotBlank(message = "Company name is required")
    private String name;

    @NotBlank(message = "Tax ID is required")
    private String taxId;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Country is required")
    private Long countryId;
}