package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setId(1L);
        country.setName("Hungary");
        country.setSign("HU");
    }

    // Test 1 — findAll returns list
    @Test
    void findAll_ShouldReturnAllCountries() {
        // GIVEN
        when(countryRepository.findAll())
                .thenReturn(Arrays.asList(country));

        // WHEN
        List<Country> result =
                countryService.findAll();

        // THEN
        assertEquals(1, result.size());
        assertEquals("Hungary",
                result.get(0).getName());
        verify(countryRepository, times(1))
                .findAll();
    }

    // Test 2 — findById returns country
    @Test
    void findById_ShouldReturnCountry() {
        // GIVEN
        when(countryRepository.findById(1L))
                .thenReturn(Optional.of(country));

        // WHEN
        Optional<Country> result =
                countryService.findById(1L);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Hungary",
                result.get().getName());
    }

    // Test 3 — findById returns empty
    @Test
    void findById_ShouldReturnEmpty_WhenNotFound() {
        // GIVEN
        when(countryRepository.findById(99L))
                .thenReturn(Optional.empty());

        // WHEN
        Optional<Country> result =
                countryService.findById(99L);

        // THEN
        assertFalse(result.isPresent());
    }

    // Test 4 — save works
    @Test
    void save_ShouldSaveAndReturnCountry() {
        // GIVEN
        when(countryRepository.save(country))
                .thenReturn(country);

        // WHEN
        Country result =
                countryService.save(country);

        // THEN
        assertNotNull(result);
        assertEquals("Hungary", result.getName());
        verify(countryRepository, times(1))
                .save(country);
    }

    // Test 5 — update works
    @Test
    void update_ShouldUpdateCountry() {
        // GIVEN
        Country updated = new Country();
        updated.setName("Albania");
        updated.setSign("AL");

        when(countryRepository.findById(1L))
                .thenReturn(Optional.of(country));
        when(countryRepository.save(any()))
                .thenReturn(country);

        // WHEN
        Country result =
                countryService.update(1L, updated);

        // THEN
        assertNotNull(result);
        verify(countryRepository, times(1))
                .findById(1L);
        verify(countryRepository, times(1))
                .save(any());
    }

    // Test 6 — update throws when not found
    @Test
    void update_ShouldThrow_WhenNotFound() {
        // GIVEN
        when(countryRepository.findById(99L))
                .thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->
                countryService.update(
                        99L, new Country()));
    }

    // Test 7 — delete works
    @Test
    void deleteById_ShouldDelete() {
        // GIVEN
        when(countryRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(countryRepository)
                .deleteById(1L);

        // WHEN
        countryService.deleteById(1L);

        // THEN
        verify(countryRepository, times(1))
                .deleteById(1L);
    }

    // Test 8 — delete throws when not found
    @Test
    void deleteById_ShouldThrow_WhenNotFound() {
        // GIVEN
        when(countryRepository.existsById(99L))
                .thenReturn(false);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->
                countryService.deleteById(99L));
    }
}