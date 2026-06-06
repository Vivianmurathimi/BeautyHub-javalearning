package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.SaleMapper;
import com.beautyhub.beautyhubbackend.dto.request.SaleRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.SaleResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.PersonService;
import com.beautyhub.beautyhubbackend.service.ProductService;
import com.beautyhub.beautyhubbackend.service.SaleService;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController
        extends AbstractApiController
        <Sale, SaleResponseDTO> {

    private final SaleService saleService;
    private final SaleMapper saleMapper;
    private final PersonService personService;
    private final ProductService productService;
    private final ShopOwnerService shopOwnerService;
    private final CompanyService companyService;

    public SaleController(
            SaleService saleService,
            SaleMapper saleMapper,
            PersonService personService,
            ProductService productService,
            ShopOwnerService shopOwnerService,
            CompanyService companyService) {
        this.saleService = saleService;
        this.saleMapper = saleMapper;
        this.personService = personService;
        this.productService = productService;
        this.shopOwnerService = shopOwnerService;
        this.companyService = companyService;
    }

    @Override
    protected AbstractService<Sale, Long>
    getService() {
        return saleService;
    }

    @Override
    protected AbstractMapper<Sale,
            SaleResponseDTO> getMapper() {
        return saleMapper;
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<SaleResponseDTO>>
    findByPerson(
            @PathVariable Long personId) {
        return ResponseEntity.ok(
                saleMapper.toDTOList(
                        saleService.findByPersonId(
                                personId)));
    }

    @GetMapping("/shopowner/{shopOwnerId}")
    public ResponseEntity<List<SaleResponseDTO>>
    findByShopOwner(
            @PathVariable Long shopOwnerId) {
        return ResponseEntity.ok(
                saleMapper.toDTOList(
                        saleService.findByShopOwnerId(
                                shopOwnerId)));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<SaleResponseDTO>>
    findByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(
                saleMapper.toDTOList(
                        saleService.findByCompanyId(
                                companyId)));
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> save(
            @Valid @RequestBody
            SaleRequestDTO requestDTO) {
        Sale sale = saleMapper.toEntity(requestDTO);
        personService.findById(
                        requestDTO.getPersonId())
                .ifPresent(sale::setPerson);
        productService.findById(
                        requestDTO.getProductId())
                .ifPresent(sale::setProduct);
        if (requestDTO.getShopOwnerId() != null) {
            shopOwnerService.findById(
                            requestDTO.getShopOwnerId())
                    .ifPresent(sale::setShopOwner);
        }
        if (requestDTO.getCompanyId() != null) {
            companyService.findById(
                            requestDTO.getCompanyId())
                    .ifPresent(sale::setCompany);
        }
        return ResponseEntity.status(201)
                .body(saleMapper.toDTO(
                        saleService.save(sale)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            SaleRequestDTO requestDTO) {
        Sale sale = saleMapper.toEntity(requestDTO);
        personService.findById(
                        requestDTO.getPersonId())
                .ifPresent(sale::setPerson);
        productService.findById(
                        requestDTO.getProductId())
                .ifPresent(sale::setProduct);
        if (requestDTO.getShopOwnerId() != null) {
            shopOwnerService.findById(
                            requestDTO.getShopOwnerId())
                    .ifPresent(sale::setShopOwner);
        }
        if (requestDTO.getCompanyId() != null) {
            companyService.findById(
                            requestDTO.getCompanyId())
                    .ifPresent(sale::setCompany);
        }
        return ResponseEntity.ok(
                saleMapper.toDTO(
                        saleService.update(
                                id, sale)));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}