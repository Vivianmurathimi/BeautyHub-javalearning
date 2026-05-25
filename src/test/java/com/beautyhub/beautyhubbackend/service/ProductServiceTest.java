package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("ORS Cosmetics");

        product = new Product();
        product.setId(1L);
        product.setName("Hair Relaxer");
        product.setPrice(new BigDecimal("29.99"));
        product.setCompany(company);
    }

    // Test 1 — findAll returns list
    @Test
    void findAll_ShouldReturnAllProducts() {
        // GIVEN
        when(productRepository.findAll())
                .thenReturn(Arrays.asList(product));

        // WHEN
        List<Product> result =
                productService.findAll();

        // THEN
        assertEquals(1, result.size());
        assertEquals("Hair Relaxer",
                result.get(0).getName());
    }

    // Test 2 — findById returns product
    @Test
    void findById_ShouldReturnProduct() {
        // GIVEN
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        // WHEN
        Optional<Product> result =
                productService.findById(1L);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Hair Relaxer",
                result.get().getName());
    }

    // Test 3 — save works
    @Test
    void save_ShouldSaveProduct() {
        // GIVEN
        when(productRepository.save(product))
                .thenReturn(product);

        // WHEN
        Product result =
                productService.save(product);

        // THEN
        assertNotNull(result);
        assertEquals("Hair Relaxer",
                result.getName());
    }

    // Test 4 — findByCompanyId works
    @Test
    void findByCompanyId_ShouldReturnProducts() {
        // GIVEN
        when(productRepository.findByCompanyId(1L))
                .thenReturn(Arrays.asList(product));

        // WHEN
        List<Product> result =
                productService.findByCompanyId(1L);

        // THEN
        assertEquals(1, result.size());
        assertEquals("Hair Relaxer",
                result.get(0).getName());
    }

    // Test 5 — delete throws when not found
    @Test
    void deleteById_ShouldThrow_WhenNotFound() {
        // GIVEN
        when(productRepository.existsById(99L))
                .thenReturn(false);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->
                productService.deleteById(99L));
    }
}
