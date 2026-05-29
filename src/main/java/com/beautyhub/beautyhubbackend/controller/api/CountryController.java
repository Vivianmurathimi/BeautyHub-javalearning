package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController
        extends AbstractApiController<Country> {

    private final CountryService countryService;

    public CountryController(
            CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    protected AbstractService<Country, Long>
    getService() {
        return countryService;
    }
}