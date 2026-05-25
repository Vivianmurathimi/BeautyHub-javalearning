package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/countries")
public class CountryWebController {

    private final CountryService countryService;

    public CountryWebController(CountryService countryService) {
        this.countryService = countryService;
    }

    // SHOW ALL COUNTRIES
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("countries",
                countryService.findAll());
        return "country/list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("country", new Country());
        return "country/form";
    }

    // SAVE COUNTRY
    // SAVE COUNTRY
    @PostMapping("/save")
    public String save(@ModelAttribute Country country) {
        if (country.getId() != null) {
            countryService.update(country.getId(), country);
        } else {
            countryService.save(country);
        }
        return "redirect:/countries";
    }
    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model) {
        countryService.findById(id)
                .ifPresent(c -> model.addAttribute("country", c));
        return "country/form";
    }
    // DELETE COUNTRY
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        countryService.deleteById(id);
        return "redirect:/countries";
    }
}