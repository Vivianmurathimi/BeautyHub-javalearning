package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.dto.request.PersonRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.PersonResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper
        extends AbstractMapper
        <Person, PersonResponseDTO> {

    @Override
    public PersonResponseDTO toDTO(Person person) {
        PersonResponseDTO dto =
                new PersonResponseDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setPersonalId(person.getPersonalId());
        dto.setAddress(person.getAddress());
        if (person.getCountry() != null) {
            dto.setCountryName(
                    person.getCountry().getName());
        }
        return dto;
    }

    @Override
    public Person toEntity(Object requestDTO) {
        PersonRequestDTO request =
                (PersonRequestDTO) requestDTO;
        Person person = new Person();
        person.setName(request.getName());
        person.setPersonalId(
                request.getPersonalId());
        person.setAddress(request.getAddress());
        return person;
    }
}