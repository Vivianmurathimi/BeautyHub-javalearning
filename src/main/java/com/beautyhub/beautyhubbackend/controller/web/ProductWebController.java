package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.service.ProductService;
import com.beautyhub.beautyhubbackend.service.CompanyService;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductWebController {

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

    // SHOW ALL PRODUCTS
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("products",
                productService.findAll());
        return "product/list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product",
                new Product());
        model.addAttribute("companies",
                companyService.findAll());
        return "product/form";
    }

    // SAVE OR UPDATE
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

    // SHOW EDIT FORM
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

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}