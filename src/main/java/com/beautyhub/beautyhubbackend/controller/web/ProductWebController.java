package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Override
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("products",
                productService.findAll());
        return "product/list";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product",
                new Product());
        model.addAttribute("companies",
                companyService.findAll());
        return "product/form";
    }

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

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute Product product,
            BindingResult result,
            @RequestParam(required = false)
            Long companyId,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (companyId != null) {
            companyRepository.findById(companyId)
                    .ifPresent(product::setCompany);
        } else {
            result.rejectValue("company",
                    "error.company",
                    "Company is required");
        }

        boolean hasErrors = result
                .getFieldErrors()
                .stream()
                .anyMatch(e ->
                        !e.getField().equals("company")
                                || companyId == null);

        if (hasErrors) {
            model.addAttribute("errors",
                    result.getAllErrors());
            model.addAttribute("companies",
                    companyService.findAll());
            return "product/form";
        }

        if (product.getId() != null) {
            productService.update(
                    product.getId(), product);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Product updated successfully!");
        } else {
            productService.save(product);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "✅ Product added successfully!");
        }
        return "redirect:/products";
    }
}