package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyWebController {

    private final CompanyService companyService;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    public CompanyWebController(CompanyService companyService,
                                CountryService countryService,
                                CountryRepository countryRepository) {
        this.companyService = companyService;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    // SHOW ALL COMPANIES
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("companies",
                companyService.findAll());
        return "company/list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("company", new Company());
        model.addAttribute("countries",
                countryService.findAll());
        return "company/form";
    }

    // SAVE COMPANY
    @PostMapping("/save")
    public String save(@ModelAttribute Company company,
                       @RequestParam(required = false)
                       Long countryId) {
        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(company::setCountry);
        }
        if (company.getId() != null) {
            companyService.update(company.getId(), company);
        } else {
            companyService.save(company);
        }
        return "redirect:/companies";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model) {
        companyService.findById(id)
                .ifPresent(c -> model.addAttribute("company", c));
        model.addAttribute("countries",
                countryService.findAll());
        return "company/form";
    }

    // DELETE COMPANY
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return "redirect:/companies";
    }
}