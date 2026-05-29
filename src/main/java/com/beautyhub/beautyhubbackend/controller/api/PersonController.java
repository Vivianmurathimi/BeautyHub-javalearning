package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.PersonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController
        extends AbstractApiController<Person> {

    private final PersonService personService;

    public PersonController(
            PersonService personService) {
        this.personService = personService;
    }

    @Override
    protected AbstractService<Person, Long>
    getService() {
        return personService;
    }
}