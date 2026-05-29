package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductWebController
        extends AbstractWebController<Product> {

    private final ProductService productService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    public ProductWebController(
            ProductService productService,
            CompanyService companyService,
            CompanyRepository companyRepository) {
        this.productService = productService;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    @Override
    protected AbstractService<Product, Long>
    getService() {
        return productService;
    }

    @Override
    protected String getEntityName() {
        return "product";
    }

    @Override
    protected String getListView() {
        return "product/list";
    }

    @Override
    protected String getFormView() {
        return "product/form";
    }

    @Override
    protected String getRedirectUrl() {
        return "/products";
    }

    @Override
    protected Product newEntity() {
        return new Product();
    }

    // Override showForm to add companies dropdown
    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product",
                new Product());
        model.addAttribute("companies",
                companyService.findAll());
        return "product/form";
    }

    // Override showEditForm to add companies dropdown
    @Override
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        productService.findById(id)
                .ifPresent(p ->
                        model.addAttribute(
                                "product", p));
        model.addAttribute("companies",
                companyService.findAll());
        return "product/form";
    }

    // Save with company dropdown
    @PostMapping("/save")
    public String save(
            @ModelAttribute Product product,
            @RequestParam(required = false)
            Long companyId) {
        if (companyId != null) {
            companyRepository.findById(companyId)
                    .ifPresent(product::setCompany);
        }
        if (product.getId() != null) {
            productService.update(
                    product.getId(), product);
        } else {
            productService.save(product);
        }
        return "redirect:/products";
    }
}