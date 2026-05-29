package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController
        extends AbstractApiController<Company> {

    private final CompanyService companyService;

    public CompanyController(
            CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    protected AbstractService<Company, Long>
    getService() {
        return companyService;
    }
}