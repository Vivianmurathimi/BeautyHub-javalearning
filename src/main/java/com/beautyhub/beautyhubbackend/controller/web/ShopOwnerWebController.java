package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopowners")
public class ShopOwnerWebController {

    private final ShopOwnerService shopOwnerService;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    public ShopOwnerWebController(
            ShopOwnerService shopOwnerService,
            CountryService countryService,
            CountryRepository countryRepository) {
        this.shopOwnerService = shopOwnerService;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    // SHOW ALL SHOP OWNERS
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("shopOwners",
                shopOwnerService.findAll());
        return "shopowner/list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("shopOwner",
                new ShopOwner());
        model.addAttribute("countries",
                countryService.findAll());
        return "shopowner/form";
    }

    // SAVE OR UPDATE
    @PostMapping("/save")
    public String save(
            @ModelAttribute ShopOwner shopOwner,
            @RequestParam(required = false)
            Long countryId) {
        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(shopOwner::setCountry);
        }
        if (shopOwner.getId() != null) {
            shopOwnerService.update(
                    shopOwner.getId(), shopOwner);
        } else {
            shopOwnerService.save(shopOwner);
        }
        return "redirect:/shopowners";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        shopOwnerService.findById(id)
                .ifPresent(s ->
                        model.addAttribute(
                                "shopOwner", s));
        model.addAttribute("countries",
                countryService.findAll());
        return "shopowner/form";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        shopOwnerService.deleteById(id);
        return "redirect:/shopowners";
    }
}