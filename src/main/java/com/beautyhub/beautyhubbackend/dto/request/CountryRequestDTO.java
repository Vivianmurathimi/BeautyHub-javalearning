package com.beautyhub.beautyhubbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryRequestDTO {

    @NotBlank(message = "Country name is required")
    private String name;

    @NotBlank(message = "Sign is required")
    @Size(max = 10,
            message = "Sign must be 10 characters or less")
    private String sign;
}