package com.beautyhub.beautyhubbackend.controller;

import com.beautyhub.beautyhubbackend.domain.Country;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setId(1L);
        country.setName("Hungary");
        country.setSign("HU");
    }

    // Test 1 — GET /api/countries returns list
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getAll_ShouldReturn200() throws Exception {
        when(countryService.findAll())
                .thenReturn(Arrays.asList(country));

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Hungary"));
    }

    // Test 2 — GET /api/countries/1 returns country
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getById_ShouldReturn200() throws Exception {
        when(countryService.findById(1L))
                .thenReturn(Optional.of(country));

        mockMvc.perform(get("/api/countries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Hungary"));
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

    // Test 4 — POST /api/countries creates country
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_ShouldReturn201() throws Exception {
        when(countryService.save(any()))
                .thenReturn(country);

        mockMvc.perform(post("/api/countries")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(country)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Hungary"));
    }

    // Test 5 — DELETE as USER returns 403
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void delete_AsUser_ShouldReturn403()
            throws Exception {
        mockMvc.perform(
                        delete("/api/countries/1"))
                .andExpect(status().isForbidden());
    }

    // Test 6 — DELETE as ADMIN returns 204
    @Test
    @WithMockUser(username = "admin",
            roles = "ADMIN")
    void delete_AsAdmin_ShouldReturn204()
            throws Exception {
        doNothing().when(countryService)
                .deleteById(1L);

        mockMvc.perform(
                        delete("/api/countries/1"))
                .andExpect(status().isNoContent());
    }

    // Test 7 — Unauthenticated returns 401
    @Test
    void getAll_Unauthenticated_ShouldReturn302()
            throws Exception {
        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isUnauthorized());
    }
}