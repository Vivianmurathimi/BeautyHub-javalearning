package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    // GET ALL → /api/sales
    @GetMapping
    public ResponseEntity<List<Sale>> findAll() {
        return ResponseEntity.ok(
                saleService.findAll());
    }

    // GET ONE → /api/sales/1
    @GetMapping("/{id}")
    public ResponseEntity<Sale> findById(
            @PathVariable Long id) {
        return saleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // GET BY PERSON → /api/sales/person/1
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<Sale>> findByPerson(
            @PathVariable Long personId) {
        return ResponseEntity.ok(
                saleService.findByPersonId(personId));
    }

    // GET BY SHOP OWNER → /api/sales/shopowner/1
    @GetMapping("/shopowner/{shopOwnerId}")
    public ResponseEntity<List<Sale>> findByShopOwner(
            @PathVariable Long shopOwnerId) {
        return ResponseEntity.ok(
                saleService
                        .findByShopOwnerId(shopOwnerId));
    }

    // GET BY COMPANY → /api/sales/company/1
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Sale>> findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                saleService.findByCompanyId(companyId));
    }

    // POST → /api/sales
    @PostMapping
    public ResponseEntity<Sale> save(
            @RequestBody Sale sale) {
        return ResponseEntity.status(201)
                .body(saleService.save(sale));
    }

    // PUT → /api/sales/1
    @PutMapping("/{id}")
    public ResponseEntity<Sale> update(
            @PathVariable Long id,
            @RequestBody Sale sale) {
        return ResponseEntity.ok(
                saleService.update(id, sale));
    }

    // DELETE → /api/sales/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        saleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
