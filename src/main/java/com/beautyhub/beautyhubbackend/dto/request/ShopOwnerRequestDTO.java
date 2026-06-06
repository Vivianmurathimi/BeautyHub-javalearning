
package com.beautyhub.beautyhubbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopOwnerRequestDTO {

    @NotBlank(message = "Shop name is required")
    private String shopName;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Country is required")
    private Long countryId;
}