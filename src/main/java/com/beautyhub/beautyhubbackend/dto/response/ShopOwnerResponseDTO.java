
package com.beautyhub.beautyhubbackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopOwnerResponseDTO {

    private Long id;
    private String shopName;
    private String ownerName;
    private String address;
    private String countryName;
}