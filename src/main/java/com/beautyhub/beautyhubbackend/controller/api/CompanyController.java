package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // GET ALL — /api/companies
    @GetMapping
    public ResponseEntity<List<Company>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    // GET ONE — /api/companies/1
    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(
            @PathVariable Long id) {
        return companyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST — /api/companies
    @PostMapping
    public ResponseEntity<Company> save(
            @RequestBody Company company) {
        return ResponseEntity.status(201)
                .body(companyService.save(company));
    }

    // PUT — /api/companies/1
    @PutMapping("/{id}")
    public ResponseEntity<Company> update(
            @PathVariable Long id,
            @RequestBody Company company) {
        return ResponseEntity.ok(
                companyService.update(id, company));
    }

    // DELETE — /api/companies/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
