package com.beautyhub.beautyhubbackend.controller.web;

import com.beautyhub.beautyhubbackend.service.CountryService;
import com.beautyhub.beautyhubbackend.service.ShopOwnerService;
import com.beautyhub.beautyhubbackend.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ShopOwnerWebController.class)
public class ShopOwnerWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopOwnerService shopOwnerService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    void shouldShowShopOwnersList() throws Exception {
        when(shopOwnerService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/shopowners"))
                .andExpect(status().isOk())
                .andExpect(view().name("shopowner/list"))
                .andExpect(model().attributeExists("shopOwners"));
    }

    @Test
    void shouldShowNewShopOwnerForm() throws Exception {
        when(countryService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/shopowners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("shopowner/form"))
                .andExpect(model().attributeExists("shopOwner"))
                .andExpect(model().attributeExists("countries"));
    }
}
