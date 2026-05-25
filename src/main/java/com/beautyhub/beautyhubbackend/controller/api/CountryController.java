package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(
            CountryService countryService) {
        this.countryService = countryService;
    }

    // GET ALL → /api/countries
    @GetMapping
    public ResponseEntity<List<Country>> findAll() {
        return ResponseEntity.ok(
                countryService.findAll());
    }

    // GET ONE → /api/countries/1
    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(
            @PathVariable Long id) {
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // POST → /api/countries
    @PostMapping
    public ResponseEntity<Country> save(
            @RequestBody Country country) {
        return ResponseEntity.status(201)
                .body(countryService.save(country));
    }

    // PUT → /api/countries/1
    @PutMapping("/{id}")
    public ResponseEntity<Country> update(
            @PathVariable Long id,
            @RequestBody Country country) {
        return ResponseEntity.ok(
                countryService.update(id, country));
    }

    // DELETE → /api/countries/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}