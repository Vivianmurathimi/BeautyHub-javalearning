package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.dto.mapper.AbstractMapper;
import com.beautyhub.beautyhubbackend.dto.mapper.PersonMapper;
import com.beautyhub.beautyhubbackend.dto.request.PersonRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.PersonResponseDTO;
import com.beautyhub.beautyhubbackend.service.AbstractService;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController
        extends AbstractApiController
        <Person, PersonResponseDTO> {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final CountryService countryService;

    public PersonController(
            PersonService personService,
            PersonMapper personMapper,
            CountryService countryService) {
        this.personService = personService;
        this.personMapper = personMapper;
        this.countryService = countryService;
    }

    @Override
    protected AbstractService<Person, Long>
    getService() {
        return personService;
    }

    @Override
    protected AbstractMapper<Person,
            PersonResponseDTO> getMapper() {
        return personMapper;
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> save(
            @Valid @RequestBody
            PersonRequestDTO requestDTO) {
        Person person = personMapper
                .toEntity(requestDTO);
        countryService.findById(
                        requestDTO.getCountryId())
                .ifPresent(person::setCountry);
        return ResponseEntity.status(201)
                .body(personMapper.toDTO(
                        personService.save(person)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody
            PersonRequestDTO requestDTO) {
        Person person = personMapper
                .toEntity(requestDTO);
        countryService.findById(
                        requestDTO.getCountryId())
                .ifPresent(person::setCountry);
        return ResponseEntity.ok(
                personMapper.toDTO(
                        personService.update(
                                id, person)));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}