package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ShopOwnerRepository shopOwnerRepository;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    public ClientService(ShopOwnerRepository shopOwnerRepository,
                         CountryService countryService,
                         CountryRepository countryRepository) {
        this.shopOwnerRepository = shopOwnerRepository;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    // CREATE
    public Client save(Client client) {
        return shopOwnerRepository.save(client);
    }

    // READ ALL
    public List<Client> findAll() {
        return shopOwnerRepository.findAll();
    }

    // READ ONE
    public Optional<Client> findById(Long id) {
        return shopOwnerRepository.findById(id);
    }

    // UPDATE
    public Client update(Long id, Client updatedClient) {
        Client existing = shopOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Client not found with id: " + id));
        existing.setName(updatedClient.getName());
        existing.setAddress(updatedClient.getAddress());
        existing.setAmount(updatedClient.getAmount());
        existing.setCountry(updatedClient.getCountry());
        return shopOwnerRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!shopOwnerRepository.existsById(id)) {
            throw new RuntimeException(
                    "Client not found with id: " + id);
        }
        shopOwnerRepository.deleteById(id);
    }
}