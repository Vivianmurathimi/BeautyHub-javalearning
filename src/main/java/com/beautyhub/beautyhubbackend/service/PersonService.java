package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.repository.AbstractRepository;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import com.beautyhub.beautyhubbackend.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonService
        extends AbstractService<Person, Long> {

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;

    public PersonService(
            PersonRepository personRepository,
            CountryRepository countryRepository) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    protected AbstractRepository<Person>
    getRepository() {
        return personRepository;
    }

    @Override
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
        existing.setAddress(
                updatedPerson.getAddress());
        if (updatedPerson.getCountry() != null) {
            countryRepository.findById(
                            updatedPerson
                                    .getCountry().getId())
                    .ifPresent(existing::setCountry);
        }
        return personRepository.save(existing);
    }
}