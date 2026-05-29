package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Override showForm to add countries dropdown
    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("company",
                new Company());
        model.addAttribute("countries",
                countryService.findAll());
        return "company/form";
    }

    // Override showEditForm to add countries dropdown
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

    // Save with country dropdown
    @PostMapping("/save")
    public String save(
            @ModelAttribute Company company,
            @RequestParam(required = false)
            Long countryId) {
        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(company::setCountry);
        }
        if (company.getId() != null) {
            companyService.update(
                    company.getId(), company);
        } else {
            companyService.save(company);
        }
        return "redirect:/companies";
    }
}