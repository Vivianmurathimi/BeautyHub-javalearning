package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractWebController
        <T extends AbstractDomain> {

    // ─── Each controller provides these ──────

    // The service to use
    protected abstract AbstractService<T, Long>
    getService();

    // Model attribute name e.g. "country"
    protected abstract String getEntityName();

    // List view e.g. "country/list"
    protected abstract String getListView();

    // Form view e.g. "country/form"
    protected abstract String getFormView();

    // Redirect URL e.g. "/countries"
    protected abstract String getRedirectUrl();

    // New empty entity e.g. new Country()
    protected abstract T newEntity();

    // ─── IDENTICAL IN ALL WEB CONTROLLERS ────

    // SHOW ALL
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

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {
        getService().deleteById(id);
        return "redirect:" + getRedirectUrl();
    }
}