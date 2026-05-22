package com.beautyhub.beautyhubbackend.controller;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // GET ALL — /api/persons
    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    // GET ONE — /api/persons/1
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(
            @PathVariable Long id) {
        return personService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST — /api/persons
    @PostMapping
    public ResponseEntity<Person> save(
            @RequestBody Person person) {
        return ResponseEntity.status(201)
                .body(personService.save(person));
    }

    // PUT — /api/persons/1
    @PutMapping("/{id}")
    public ResponseEntity<Person> update(
            @PathVariable Long id,
            @RequestBody Person person) {
        return ResponseEntity.ok(
                personService.update(id, person));
    }

    // DELETE — /api/persons/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}