package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/companies")
public class CompanyWebController
        extends AbstractWebController<Company> {

    private final CompanyService companyService;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    public CompanyWebController(
            CompanyService companyService,
            CountryService countryService,
            CountryRepository countryRepository) {
        this.companyService = companyService;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @Override
    protected AbstractService<Company, Long>
    getService() {
        return companyService;
    }

    @Override
    protected String getEntityName() {
        return "company";
    }

    @Override
    protected String getListView() {
        return "company/list";
    }

    @Override
    protected String getFormView() {
        return "company/form";
    }

    @Override
    protected String getRedirectUrl() {
        return "/companies";
    }

    @Override
    protected Company newEntity() {
        return new Company();
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("companies",
                companyService.findAll());
        return "company/list";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("company",
                new Company());
        model.addAttribute("countries",
                countryService.findAll());
        return "company/form";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        companyService.findById(id)
                .ifPresent(c ->
                        model.addAttribute(
                                "company", c));
        model.addAttribute("countries",
                countryService.findAll());
        return "company/form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute Company company,
            BindingResult result,
            @RequestParam(required = false)
            Long countryId,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Set country from dropdown first
        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(company::setCountry);
        } else {
            result.rejectValue("country",
                    "error.country",
                    "Country is required");
        }

        // Check errors excluding country
        // if country was selected
        boolean hasErrors = result
                .getFieldErrors()
                .stream()
                .anyMatch(e ->
                        !e.getField().equals("country")
                                || countryId == null);

        if (hasErrors) {
            model.addAttribute("errors",
                    result.getAllErrors());
            model.addAttribute("countries",
                    countryService.findAll());
            return "company/form";
        }

        if (company.getId() != null) {
            companyService.update(
                    company.getId(), company);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Company updated successfully!");
        } else {
            companyService.save(company);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Company added successfully!");
        }
        return "redirect:/companies";
    }
}