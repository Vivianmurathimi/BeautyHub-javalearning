package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;

    public PersonService(
            PersonRepository personRepository,
            CountryRepository countryRepository) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
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
    public Person update(Long id,
                         Person updatedPerson) {
        Person existing = personRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Person not found: " + id));
        existing.setName(updatedPerson.getName());
        existing.setPersonalId(
                updatedPerson.getPersonalId());
        existing.setAddress(updatedPerson.getAddress());

        // Update country if provided
        if (updatedPerson.getCountry() != null) {
            Country country = countryRepository
                    .findById(updatedPerson
                            .getCountry().getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Country not found"));
            existing.setCountry(country);
        }
        return personRepository.save(existing);
    }

    // DELETE
    public void deleteById(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException(
                    "Person not found: " + id);
        }
        personRepository.deleteById(id);
    }
}