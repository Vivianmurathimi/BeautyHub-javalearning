package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.domain.Client;
import com.beautyhub.beautyhubbackend.repository.PersonRepository;
import com.beautyhub.beautyhubbackend.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final ClientService clientService;

    public PersonService(PersonRepository personRepository,
                         ClientService clientService) {
        this.personRepository = personRepository;
        this.clientService = clientService;
    }

    // CREATE
    public Person save(Person person) {
        return personRepository.save(person);
    }

    // READ ALL
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    // READ ONE
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    // UPDATE
    public Person update(Long id, Person updatedPerson) {
        Person existing = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Person not found with id: " + id));
        // Update Client fields
        existing.setName(updatedPerson.getName());
        existing.setAddress(updatedPerson.getAddress());
        existing.setAmount(updatedPerson.getAmount());
        existing.setCountry(updatedPerson.getCountry());

        existing.setPersonalId(updatedPerson.getPersonalId());
        return personRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException(
                    "Person not found with id: " + id);
        }
        personRepository.deleteById(id);
    }
}