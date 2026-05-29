package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Override showForm to add countries dropdown
    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("person",
                new Person());
        model.addAttribute("countries",
                countryService.findAll());
        return "person/form";
    }

    // Override showEditForm to add countries dropdown
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

    // Save with country dropdown
    @PostMapping("/save")
    public String save(
            @ModelAttribute Person person,
            @RequestParam(required = false)
            Long countryId) {
        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(person::setCountry);
        }
        if (person.getId() != null) {
            personService.update(
                    person.getId(), person);
        } else {
            personService.save(person);
        }
        return "redirect:/persons";
    }
}