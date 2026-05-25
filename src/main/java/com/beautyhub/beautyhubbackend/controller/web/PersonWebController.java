package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.service.PersonService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/persons")
public class PersonWebController {

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

    // SHOW ALL PERSONS
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("persons",
                personService.findAll());
        return "person/list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("person",
                new Person());
        model.addAttribute("countries",
                countryService.findAll());
        return "person/form";
    }

    // SAVE OR UPDATE
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

    // SHOW EDIT FORM
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

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        personService.deleteById(id);
        return "redirect:/persons";
    }
}