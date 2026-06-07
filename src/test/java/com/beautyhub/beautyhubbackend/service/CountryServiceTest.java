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
import static org.mockito.ArgumentMatchers.any;
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

    // Test 1 — save returns saved country
    @Test
    void save_ShouldReturnSavedCountry() {
        when(countryRepository.save(any()))
                .thenReturn(country);

        Country result =
                countryService.save(country);

        assertNotNull(result);
        assertEquals("Hungary", result.getName());
        assertEquals("HU", result.getSign());
        verify(countryRepository, times(1))
                .save(country);
    }

    // Test 2 — findAll returns list
    @Test
    void findAll_ShouldReturnList() {
        when(countryRepository.findAll())
                .thenReturn(Arrays.asList(country));

        List<Country> result =
                countryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hungary",
                result.get(0).getName());
    }

    // Test 3 — findById returns country
    @Test
    void findById_ShouldReturnCountry() {
        when(countryRepository.findById(1L))
                .thenReturn(Optional.of(country));

        Optional<Country> result =
                countryService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Hungary",
                result.get().getName());
    }

    // Test 4 — findById returns empty
    @Test
    void findById_ShouldReturnEmpty_WhenNotFound() {
        when(countryRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Country> result =
                countryService.findById(99L);

        assertFalse(result.isPresent());
    }

    // Test 5 — update returns updated country
    @Test
    void update_ShouldReturnUpdatedCountry() {
        Country updated = new Country();
        updated.setName("France");
        updated.setSign("FR");

        when(countryRepository.findById(1L))
                .thenReturn(Optional.of(country));
        when(countryRepository.save(any()))
                .thenReturn(country);

        Country result =
                countryService.update(1L, updated);

        assertNotNull(result);
        verify(countryRepository, times(1))
                .save(any());
    }

    // Test 6 — update throws when not found
    @Test
    void update_ShouldThrow_WhenNotFound() {
        when(countryRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> countryService.update(
                        99L, country));
    }

    // Test 7 — deleteById calls repository
    @Test
    void deleteById_ShouldDelete() {
        when(countryRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(countryRepository)
                .deleteById(1L);

        countryService.deleteById(1L);

        verify(countryRepository, times(1))
                .deleteById(1L);
    }

    // Test 8 — deleteById throws when not found
    @Test
    void deleteById_ShouldThrow_WhenNotFound() {
        when(countryRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> countryService.deleteById(99L));
    }
}