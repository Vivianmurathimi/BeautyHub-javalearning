package com.beautyhub.beautyhubbackend.dto.mapper;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.dto.request.CompanyRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CompanyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CompanyMapperTest {

    private CompanyMapper companyMapper;
    private Company company;
    private Country country;
    private CompanyRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        companyMapper = new CompanyMapper();

        country = new Country();
        country.setId(1L);
        country.setName("United States");
        country.setSign("US");

        company = new Company();
        company.setId(1L);
        company.setName("ORS Cosmetics");
        company.setTaxId("TAX001");
        company.setAddress("123 Beauty Ave");
        company.setCountry(country);

        requestDTO = new CompanyRequestDTO();
        requestDTO.setName("ORS Cosmetics");
        requestDTO.setTaxId("TAX001");
        requestDTO.setAddress("123 Beauty Ave");
        requestDTO.setCountryId(1L);
    }

    // Test 1 — toDTO maps all fields correctly
    @Test
    void toDTO_ShouldMapAllFields() {
        CompanyResponseDTO dto =
                companyMapper.toDTO(company);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("ORS Cosmetics",
                dto.getName());
        assertEquals("TAX001", dto.getTaxId());
        assertEquals("123 Beauty Ave",
                dto.getAddress());
        assertEquals("United States",
                dto.getCountryName());
    }

    // Test 2 — toDTO with null country
    @Test
    void toDTO_ShouldHandle_NullCountry() {
        company.setCountry(null);

        CompanyResponseDTO dto =
                companyMapper.toDTO(company);

        assertNotNull(dto);
        assertNull(dto.getCountryName());
    }

    // Test 3 — toEntity maps all fields
    @Test
    void toEntity_ShouldMapAllFields() {
        Company result =
                companyMapper.toEntity(requestDTO);

        assertNotNull(result);
        assertEquals("ORS Cosmetics",
                result.getName());
        assertEquals("TAX001",
                result.getTaxId());
        assertEquals("123 Beauty Ave",
                result.getAddress());
    }

    // Test 4 — toEntity has no country set
    @Test
    void toEntity_ShouldNotSetCountry() {
        Company result =
                companyMapper.toEntity(requestDTO);

        // Country is set separately in controller
        assertNull(result.getCountry());
    }

    // Test 5 — toDTOList maps list correctly
    @Test
    void toDTOList_ShouldMapAllEntities() {
        Company company2 = new Company();
        company2.setId(2L);
        company2.setName("Loreal Paris");
        company2.setTaxId("TAX002");
        company2.setAddress("456 Fashion St");
        company2.setCountry(country);

        List<CompanyResponseDTO> result =
                companyMapper.toDTOList(
                        Arrays.asList(company,
                                company2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ORS Cosmetics",
                result.get(0).getName());
        assertEquals("Loreal Paris",
                result.get(1).getName());
    }

    // Test 6 — toDTOList with empty list
    @Test
    void toDTOList_ShouldReturn_EmptyList() {
        List<CompanyResponseDTO> result =
                companyMapper.toDTOList(
                        Arrays.asList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}