package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopowners")
public class ShopOwnerWebController
        extends AbstractWebController<ShopOwner> {

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

    @Override
    protected AbstractService<ShopOwner, Long>
    getService() {
        return shopOwnerService;
    }

    @Override
    protected String getEntityName() {
        return "shopOwner";
    }

    @Override
    protected String getListView() {
        return "shopowner/list";
    }

    @Override
    protected String getFormView() {
        return "shopowner/form";
    }

    @Override
    protected String getRedirectUrl() {
        return "/shopowners";
    }

    @Override
    protected ShopOwner newEntity() {
        return new ShopOwner();
    }

    // Override showForm to add countries dropdown
    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("shopOwner",
                new ShopOwner());
        model.addAttribute("countries",
                countryService.findAll());
        return "shopowner/form";
    }

    // Override showEditForm to add countries dropdown
    @Override
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

    // Save with country dropdown
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
}