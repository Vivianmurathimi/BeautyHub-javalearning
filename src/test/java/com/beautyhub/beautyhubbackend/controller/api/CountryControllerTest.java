package com.beautyhub.beautyhubbackend.controller.api;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.dto.mapper.CountryMapper;
import com.beautyhub.beautyhubbackend.dto.request.CountryRequestDTO;
import com.beautyhub.beautyhubbackend.dto.response.CountryResponseDTO;
import com.beautyhub.beautyhubbackend.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private CountryMapper countryMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Country country;
    private CountryRequestDTO requestDTO;
    private CountryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setId(1L);
        country.setName("Hungary");
        country.setSign("HU");

        requestDTO = new CountryRequestDTO();
        requestDTO.setName("Hungary");
        requestDTO.setSign("HU");

        responseDTO = new CountryResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Hungary");
        responseDTO.setSign("HU");
    }

    // Test 1 — GET /api/countries returns list
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getAll_ShouldReturn200()
            throws Exception {
        when(countryService.findAll())
                .thenReturn(Arrays.asList(country));
        when(countryMapper.toDTOList(any()))
                .thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Hungary"))
                .andExpect(jsonPath("$[0].sign")
                        .value("HU"));
    }

    // Test 2 — GET /api/countries/1
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getById_ShouldReturn200()
            throws Exception {
        when(countryService.findById(1L))
                .thenReturn(Optional.of(country));
        when(countryMapper.toDTO(any()))
                .thenReturn(responseDTO);

        mockMvc.perform(get("/api/countries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Hungary"))
                .andExpect(jsonPath("$.sign")
                        .value("HU"));
    }

    // Test 3 — GET /api/countries/99 returns 404
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getById_ShouldReturn404_WhenNotFound()
            throws Exception {
        when(countryService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/countries/99"))
                .andExpect(status().isNotFound());
    }

    // Test 4 — POST /api/countries returns 201
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_ShouldReturn201()
            throws Exception {
        when(countryMapper.toEntity(any()))
                .thenReturn(country);
        when(countryService.save(any()))
                .thenReturn(country);
        when(countryMapper.toDTO(any()))
                .thenReturn(responseDTO);

        mockMvc.perform(
                        post("/api/countries")
                                .with(csrf())
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(objectMapper
                                        .writeValueAsString(
                                                requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Hungary"));
    }

    // Test 5 — POST with empty name returns 400
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_WithEmptyName_ShouldReturn400()
            throws Exception {
        CountryRequestDTO emptyRequest =
                new CountryRequestDTO();
        emptyRequest.setName("");
        emptyRequest.setSign("HU");

        mockMvc.perform(
                        post("/api/countries")
                                .with(csrf())
                                .contentType(
                                        MediaType.APPLICATION_JSON)
                                .content(objectMapper
                                        .writeValueAsString(
                                                emptyRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test 6 — DELETE as USER returns 403
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void delete_AsUser_ShouldReturn403()
            throws Exception {
        mockMvc.perform(
                        delete("/api/countries/1")
                                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    // Test 7 — DELETE as ADMIN returns 204
    @Test
    @WithMockUser(username = "admin",
            roles = "ADMIN")
    void delete_AsAdmin_ShouldReturn204()
            throws Exception {
        doNothing().when(countryService)
                .deleteById(1L);

        mockMvc.perform(
                        delete("/api/countries/1")
                                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    // Test 8 — Unauthenticated returns 401
    @Test
    void getAll_Unauthenticated_ShouldReturn401()
            throws Exception {
        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isUnauthorized());
    }
}