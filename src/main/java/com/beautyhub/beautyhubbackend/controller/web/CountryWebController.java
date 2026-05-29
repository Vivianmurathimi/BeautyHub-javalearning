package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/countries")
public class CountryWebController
        extends AbstractWebController<Country> {

    private final CountryService countryService;

    public CountryWebController(
            CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    protected AbstractService<Country, Long>
    getService() {
        return countryService;
    }

    @Override
    protected String getEntityName() {
        return "country";
    }

    @Override
    protected String getListView() {
        return "country/list";
    }

    @Override
    protected String getFormView() {
        return "country/form";
    }

    @Override
    protected String getRedirectUrl() {
        return "/countries";
    }

    @Override
    protected Country newEntity() {
        return new Country();
    }

    // Save is unique — no dropdown for Country
    @PostMapping("/save")
    public String save(
            @ModelAttribute Country country) {
        if (country.getId() != null) {
            countryService.update(
                    country.getId(), country);
        } else {
            countryService.save(country);
        }
        return "redirect:/countries";
    }
}