package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Override
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("countries",
                countryService.findAll());
        return "country/list";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("country",
                new Country());
        return "country/form";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        countryService.findById(id)
                .ifPresent(c ->
                        model.addAttribute(
                                "country", c));
        return "country/form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute Country country,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errors",
                    result.getAllErrors());
            return "country/form";
        }
        if (country.getId() != null) {
            countryService.update(
                    country.getId(), country);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Country updated successfully!");
        } else {
            countryService.save(country);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Country added successfully!");
        }
        return "redirect:/countries";
    }
}