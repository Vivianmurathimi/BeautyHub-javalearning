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
import static org.mockito.ArgumentMatchers.any;
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
        company.setTaxId("TAX001");
        company.setAddress("123 Beauty Ave");

        product = new Product();
        product.setId(1L);
        product.setName("Hair Relaxer");
        product.setDescription("Professional kit");
        product.setPrice(new BigDecimal("29.99"));
        product.setCompany(company);
    }

    // Test 1 — save returns saved product
    @Test
    void save_ShouldReturnSavedProduct() {
        when(productRepository.save(any()))
                .thenReturn(product);

        Product result =
                productService.save(product);

        assertNotNull(result);
        assertEquals("Hair Relaxer",
                result.getName());
        assertEquals(new BigDecimal("29.99"),
                result.getPrice());
        verify(productRepository, times(1))
                .save(product);
    }

    // Test 2 — findAll returns list
    @Test
    void findAll_ShouldReturnList() {
        when(productRepository.findAll())
                .thenReturn(Arrays.asList(product));

        List<Product> result =
                productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hair Relaxer",
                result.get(0).getName());
    }

    // Test 3 — findById returns product
    @Test
    void findById_ShouldReturnProduct() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Optional<Product> result =
                productService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Hair Relaxer",
                result.get().getName());
    }

    // Test 4 — findById returns empty
    @Test
    void findById_ShouldReturnEmpty_WhenNotFound() {
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Product> result =
                productService.findById(99L);

        assertFalse(result.isPresent());
    }

    // Test 5 — findByCompanyId returns list
    @Test
    void findByCompanyId_ShouldReturnList() {
        when(productRepository
                .findByCompanyId(1L))
                .thenReturn(Arrays.asList(product));

        List<Product> result =
                productService.findByCompanyId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hair Relaxer",
                result.get(0).getName());
    }

    // Test 6 — update returns updated product
    @Test
    void update_ShouldReturnUpdatedProduct() {
        Product updated = new Product();
        updated.setName("Shampoo");
        updated.setDescription("Moisturizing");
        updated.setPrice(new BigDecimal("12.99"));
        updated.setCompany(company);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(productRepository.save(any()))
                .thenReturn(product);

        Product result =
                productService.update(1L, updated);

        assertNotNull(result);
        verify(productRepository, times(1))
                .save(any());
    }

    // Test 7 — update throws when not found
    @Test
    void update_ShouldThrow_WhenNotFound() {
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productService.update(
                        99L, product));
    }

    // Test 8 — deleteById calls repository
    @Test
    void deleteById_ShouldDelete() {
        when(productRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(productRepository)
                .deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1))
                .deleteById(1L);
    }

    // Test 9 — deleteById throws when not found
    @Test
    void deleteById_ShouldThrow_WhenNotFound() {
        when(productRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> productService
                        .deleteById(99L));
    }
}