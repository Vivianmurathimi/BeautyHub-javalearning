package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/persons")
public class PersonWebController
        extends AbstractWebController<Person> {

    private final PersonService personService;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    public PersonWebController(
            PersonService personService,
            CountryService countryService,
            CountryRepository countryRepository) {
        this.personService = personService;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @Override
    protected AbstractService<Person, Long>
    getService() {
        return personService;
    }

    @Override
    protected String getEntityName() {
        return "person";
    }

    @Override
    protected String getListView() {
        return "person/list";
    }

    @Override
    protected String getFormView() {
        return "person/form";
    }

    @Override
    protected String getRedirectUrl() {
        return "/persons";
    }

    @Override
    protected Person newEntity() {
        return new Person();
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("persons",
                personService.findAll());
        return "person/list";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("person",
                new Person());
        model.addAttribute("countries",
                countryService.findAll());
        return "person/form";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        personService.findById(id)
                .ifPresent(p ->
                        model.addAttribute(
                                "person", p));
        model.addAttribute("countries",
                countryService.findAll());
        return "person/form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute Person person,
            BindingResult result,
            @RequestParam(required = false)
            Long countryId,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(person::setCountry);
        } else {
            result.rejectValue("country",
                    "error.country",
                    "Country is required");
        }

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
            return "person/form";
        }

        if (person.getId() != null) {
            personService.update(
                    person.getId(), person);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Person updated successfully!");
        } else {
            personService.save(person);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Person added successfully!");
        }
        return "redirect:/persons";
    }
}