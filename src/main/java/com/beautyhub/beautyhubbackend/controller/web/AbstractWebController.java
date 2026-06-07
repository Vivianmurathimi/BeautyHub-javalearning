package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class AbstractWebController
        <T extends AbstractDomain> {

    protected abstract AbstractService<T, Long>
    getService();

    protected abstract String getEntityName();

    protected abstract String getListView();

    protected abstract String getFormView();

    protected abstract String getRedirectUrl();

    protected abstract T newEntity();

    // SHOW ALL — overridden in each controller
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute(
                getEntityName() + "s",
                getService().findAll());
        return getListView();
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute(
                getEntityName(),
                newEntity());
        return getFormView();
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {
        getService().findById(id)
                .ifPresent(entity ->
                        model.addAttribute(
                                getEntityName(),
                                entity));
        return getFormView();
    }

    // DELETE — shared for ALL controllers

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        getService().deleteById(id);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "✅ Deleted successfully!");
        return "redirect:" + getRedirectUrl();
    }
}