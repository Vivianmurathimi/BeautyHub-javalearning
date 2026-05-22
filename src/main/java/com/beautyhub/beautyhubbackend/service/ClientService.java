package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.repository.ClientRepository;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final CountryService countryService;

    public ClientService(ClientRepository clientRepository,
                         CountryService countryService) {
        this.clientRepository = clientRepository;
        this.countryService = countryService;
    }

    // CREATE
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    // READ ALL
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    // READ ONE
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    // UPDATE
    public Client update(Long id, Client updatedClient) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Client not found with id: " + id));
        existing.setName(updatedClient.getName());
        existing.setAddress(updatedClient.getAddress());
        existing.setAmount(updatedClient.getAmount());
        existing.setCountry(updatedClient.getCountry());
        return clientRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException(
                    "Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}