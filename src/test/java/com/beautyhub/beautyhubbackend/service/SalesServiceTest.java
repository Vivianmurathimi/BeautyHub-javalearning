package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.CompanyRepository;
import com.beautyhub.beautyhubbackend.repository.PersonRepository;
import com.beautyhub.beautyhubbackend.repository.ProductRepository;
import com.beautyhub.beautyhubbackend.repository.SaleRepository;
import com.beautyhub.beautyhubbackend.repository.ShopOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ShopOwnerRepository shopOwnerRepository;
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private SaleService saleService;

    private Sale sale;
    private Person person;
    private Product product;
    private ShopOwner shopOwner;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("John Doe");

        product = new Product();
        product.setId(1L);
        product.setName("Hair Relaxer");

        shopOwner = new ShopOwner();
        shopOwner.setId(1L);
        shopOwner.setShopName("Vivi Beauty");

        sale = new Sale();
        sale.setPerson(person);
        sale.setProduct(product);
        sale.setShopOwner(shopOwner);
        sale.setCompany(null);
        sale.setQuantity(2);
        sale.setUnitPrice(
                new BigDecimal("35.99"));
    }

    // Test 1 — Sale with ShopOwner saves correctly
    @Test
    void save_WithShopOwner_ShouldCalculateTotal() {
        // GIVEN
        when(saleRepository.save(any()))
                .thenReturn(sale);

        // WHEN
        Sale result = saleService.save(sale);

        // THEN
        assertNotNull(result);
        // Total should be 2 x 35.99 = 71.98
        assertEquals(
                new BigDecimal("71.98"),
                sale.getTotalPrice());
    }

    // Test 2 — Sale with both sellers throws
    @Test
    void save_WithBothSellers_ShouldThrow() {
        // GIVEN — set both sellers
        Company company = new Company();
        company.setId(1L);
        sale.setCompany(company);
        // Now sale has BOTH shopOwner AND company

        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->
                saleService.save(sale));
    }

    // Test 3 — Sale with no seller throws
    @Test
    void save_WithNoSeller_ShouldThrow() {
        // GIVEN — remove both sellers
        sale.setShopOwner(null);
        sale.setCompany(null);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->
                saleService.save(sale));
    }

    // Test 4 — delete throws when not found
    @Test
    void deleteById_ShouldThrow_WhenNotFound() {
        // GIVEN
        when(saleRepository.existsById(99L))
                .thenReturn(false);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () ->
                saleService.deleteById(99L));
    }
}