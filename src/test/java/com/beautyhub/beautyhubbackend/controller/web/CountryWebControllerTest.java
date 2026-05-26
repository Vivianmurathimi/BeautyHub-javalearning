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

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    // Test 1 — Unauthenticated returns 401
    @Test
    void getAll_Unauthenticated_ShouldReturn401()
            throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(status().isUnauthorized()); // 401
    }

    // Test 2 — GET /countries shows list page
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void getAll_ShouldReturnListView()
            throws Exception {
        when(countryService.findAll())
                .thenReturn(Arrays.asList(country));

        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(view()
                        .name("country/list"))
                .andExpect(model()
                        .attributeExists("countries"));
    }

    // Test 3 — GET /countries/new shows form
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void showForm_ShouldReturnFormView()
            throws Exception {
        mockMvc.perform(get("/countries/new"))
                .andExpect(status().isOk())
                .andExpect(view()
                        .name("country/form"))
                .andExpect(model()
                        .attributeExists("country"));
    }

    // Test 4 — GET /countries/edit/1 shows edit form
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void showEditForm_ShouldReturnFormView()
            throws Exception {
        when(countryService.findById(1L))
                .thenReturn(Optional.of(country));

        mockMvc.perform(get("/countries/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view()
                        .name("country/form"))
                .andExpect(model()
                        .attribute("country", country));
    }

    // Test 5 — POST /countries/save creates country
    // and redirects to list
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void save_ShouldRedirectToList()
            throws Exception {
        when(countryService.save(any()))
                .thenReturn(country);

        mockMvc.perform(post("/countries/save")
                        .with(csrf())  // CSRF token required
                        .param("name", "Hungary")
                        .param("sign", "HU"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl(
                        "/countries"));
    }

    // Test 6 — POST /countries/save updates country
    // when id is present
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void update_ShouldRedirectToList()
            throws Exception {
        when(countryService.update(
                anyLong(), any()))
                .thenReturn(country);

        mockMvc.perform(post("/countries/save")
                        .with(csrf())
                        .param("id", "1")
                        .param("name", "Hungary Updated")
                        .param("sign", "HU"))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl(
                        "/countries"));
    }

    // Test 7 — POST /countries/delete/1
    // as ADMIN deletes and redirects
    @Test
    @WithMockUser(username = "admin",
            roles = "ADMIN")
    void delete_AsAdmin_ShouldRedirectToList()
            throws Exception {
        doNothing().when(countryService)
                .deleteById(1L);

        mockMvc.perform(post("/countries/delete/1")
                        .with(csrf()))
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl(
                        "/countries"));
    }

    // Test 8 — POST /countries/delete/1
    // as USER gets access denied
    @Test
    @WithMockUser(username = "user",
            roles = "USER")
    void delete_AsUser_ShouldBeForbidden()
            throws Exception {
        mockMvc.perform(post("/countries/delete/1")
                        .with(csrf()))
                .andExpect(status()
                        .isForbidden());
    }
}