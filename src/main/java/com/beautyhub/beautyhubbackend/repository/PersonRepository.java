package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
        extends AbstractRepository<Person> {
}