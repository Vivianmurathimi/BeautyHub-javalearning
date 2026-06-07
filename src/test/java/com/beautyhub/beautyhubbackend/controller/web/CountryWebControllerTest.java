package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.domain.Country;
import com.beautyhub.beautyhubbackend.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;

@SpringBootTest
@AutoConfigureMockMvc
class CountryWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setId(1L);
        country.setName("Hungary");
        country.setSign("HU");
    }

    // Test 1 — GET /countries returns list page
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getAll_ShouldReturn200()
            throws Exception {
        when(countryService.findAll())
                .thenReturn(Arrays.asList(country));

        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(view().name(
                        "country/list"))
                .andExpect(model().attributeExists(
                        "countries"));
    }

    // Test 2 — GET /countries/new returns form
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void showForm_ShouldReturn200()
            throws Exception {
        mockMvc.perform(get("/countries/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(
                        "country/form"))
                .andExpect(model().attributeExists(
                        "country"));
    }

    // Test 3 — GET /countries/edit/1 returns form
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void showEditForm_ShouldReturn200()
            throws Exception {
        when(countryService.findById(1L))
                .thenReturn(Optional.of(country));

        mockMvc.perform(get("/countries/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(
                        "country/form"))
                .andExpect(model().attributeExists(
                        "country"));
    }

    // Test 4 — POST /countries/save with valid data
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_WithValidData_ShouldRedirect()
            throws Exception {
        when(countryService.save(any()))
                .thenReturn(country);

        mockMvc.perform(
                        post("/countries/save")
                                .with(csrf())
                                .param("name", "Hungary")
                                .param("sign", "HU"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(
                        "/countries"));
    }

    // Test 5 — POST with empty name stays on form
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_WithEmptyName_ShouldStayOnForm()
            throws Exception {
        mockMvc.perform(
                        post("/countries/save")
                                .with(csrf())
                                .param("name", "")
                                .param("sign", "HU"))
                .andExpect(status().isOk())
                .andExpect(view().name(
                        "country/form"));
    }

    // Test 6 — POST with empty sign stays on form
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_WithEmptySign_ShouldStayOnForm()
            throws Exception {
        mockMvc.perform(
                        post("/countries/save")
                                .with(csrf())
                                .param("name", "Hungary")
                                .param("sign", ""))
                .andExpect(status().isOk())
                .andExpect(view().name(
                        "country/form"));
    }

    // Test 7 — POST update existing country
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void update_ShouldRedirect()
            throws Exception {
        when(countryService.update(
                eq(1L), any()))
                .thenReturn(country);

        mockMvc.perform(
                        post("/countries/save")
                                .with(csrf())
                                .param("id", "1")
                                .param("name", "Hungary")
                                .param("sign", "HU"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(
                        "/countries"));
    }

    // Test 8 — DELETE as ADMIN redirects
    @Test
    @WithMockUser(username = "admin",
            roles = "ADMIN")
    void delete_AsAdmin_ShouldRedirect()
            throws Exception {
        doNothing().when(countryService)
                .deleteById(1L);

        mockMvc.perform(
                        post("/countries/delete/1")
                                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(
                        "/countries"));
    }

    // Test 9 — DELETE as USER returns 403
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void delete_AsUser_ShouldReturn403()
            throws Exception {
        mockMvc.perform(
                        post("/countries/delete/1")
                                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    // Test 10 — Unauthenticated returns 401
// because Spring Security blocks before redirect
    @Test
    void getAll_Unauthenticated_ShouldReturn401()
            throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(status()
                        .isUnauthorized());
    }
}