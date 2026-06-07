package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.dto.request.CountryRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CountryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CountryMapperTest {

    private CountryMapper countryMapper;
    private Country country;
    private CountryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        countryMapper = new CountryMapper();

        country = new Country();
        country.setId(1L);
        country.setName("Hungary");
        country.setSign("HU");

        requestDTO = new CountryRequestDTO();
        requestDTO.setName("Hungary");
        requestDTO.setSign("HU");
    }

    // Test 1 — toDTO maps all fields correctly
    @Test
    void toDTO_ShouldMapAllFields() {
        CountryResponseDTO dto =
                countryMapper.toDTO(country);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Hungary", dto.getName());
        assertEquals("HU", dto.getSign());
    }

    // Test 2 — toDTO with null returns null fields
    @Test
    void toDTO_ShouldHandle_NullName() {
        country.setName(null);
        country.setSign(null);

        CountryResponseDTO dto =
                countryMapper.toDTO(country);

        assertNotNull(dto);
        assertNull(dto.getName());
        assertNull(dto.getSign());
    }

    // Test 3 — toEntity maps all fields correctly
    @Test
    void toEntity_ShouldMapAllFields() {
        Country result =
                countryMapper.toEntity(requestDTO);

        assertNotNull(result);
        assertEquals("Hungary", result.getName());
        assertEquals("HU", result.getSign());
    }

    // Test 4 — toEntity has no ID
    @Test
    void toEntity_ShouldNotHaveId() {
        Country result =
                countryMapper.toEntity(requestDTO);

        assertNull(result.getId());
    }

    // Test 5 — toDTOList maps list correctly
    @Test
    void toDTOList_ShouldMapAllEntities() {
        Country country2 = new Country();
        country2.setId(2L);
        country2.setName("France");
        country2.setSign("FR");

        List<CountryResponseDTO> result =
                countryMapper.toDTOList(
                        Arrays.asList(country,
                                country2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hungary",
                result.get(0).getName());
        assertEquals("France",
                result.get(1).getName());
    }

    // Test 6 — toDTOList with empty list
    @Test
    void toDTOList_ShouldReturn_EmptyList() {
        List<CountryResponseDTO> result =
                countryMapper.toDTOList(
                        Arrays.asList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
