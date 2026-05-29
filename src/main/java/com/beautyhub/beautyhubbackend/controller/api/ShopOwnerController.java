package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopowners")
public class ShopOwnerController
        extends AbstractApiController<ShopOwner> {

    private final ShopOwnerService shopOwnerService;

    public ShopOwnerController(
            ShopOwnerService shopOwnerService) {
        this.shopOwnerService = shopOwnerService;
    }

    @Override
    protected AbstractService<ShopOwner, Long>
    getService() {
        return shopOwnerService;
    }
}