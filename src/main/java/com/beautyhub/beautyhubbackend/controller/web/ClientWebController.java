package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.service.ClientService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientWebController {

    private final ClientService clientService;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    public ClientWebController(ClientService clientService,
                               CountryService countryService,
                                CountryRepository countryRepository) {
        this.clientService = clientService;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    // SHOW ALL CLIENTS
    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("clients",
                clientService.findAll());
        return "client/list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("countries",
                countryService.findAll());
        return "client/form";
    }

    // SAVE CLIENT
    @PostMapping("/save")
    public String save(@ModelAttribute Client client,
                       @RequestParam(required = false)
                       Long countryId) {
        if (countryId != null) {
            countryRepository.findById(countryId)
                    .ifPresent(client::setCountry);
        }
        if (client.getId() != null) {
            clientService.update(client.getId(), client);
        } else {
            clientService.save(client);
        }
        return "redirect:/clients";
    }
    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model) {
        clientService.findById(id)
                .ifPresent(c -> model.addAttribute("client", c));
        model.addAttribute("countries",
                countryService.findAll());
        return "client/form";
    }

    // DELETE CLIENT
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return "redirect:/clients";
    }
}