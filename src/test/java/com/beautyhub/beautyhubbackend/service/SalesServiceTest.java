package com.beautyhub.beautyhubbackend.service;

import com.beautyhub.beautyhubbackend.domain.Company;
import com.beautyhub.beautyhubbackend.domain.Person;
import com.beautyhub.beautyhubbackend.domain.Product;
import com.beautyhub.beautyhubbackend.domain.Sale;
import com.beautyhub.beautyhubbackend.domain.ShopOwner;
import com.beautyhub.beautyhubbackend.repository.SaleRepository;
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
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    private Sale sale;
    private Person person;
    private Product product;
    private ShopOwner shopOwner;
    private Company company;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setPersonalId("PID001");
        person.setAddress("5 Rose St");

        product = new Product();
        product.setId(1L);
        product.setName("Hair Relaxer");
        product.setPrice(new BigDecimal("29.99"));

        shopOwner = new ShopOwner();
        shopOwner.setId(1L);
        shopOwner.setShopName("Vivi Beauty Shop");
        shopOwner.setOwnerName("Vivian Murathimi");
        shopOwner.setAddress("10 Main St");

        company = new Company();
        company.setId(1L);
        company.setName("ORS Cosmetics");
        company.setTaxId("TAX001");
        company.setAddress("123 Beauty Ave");

        sale = new Sale();
        sale.setId(1L);
        sale.setPerson(person);
        sale.setProduct(product);
        sale.setShopOwner(shopOwner);
        sale.setCompany(null);
        sale.setQuantity(1);
        sale.setUnitPrice(new BigDecimal("35.99"));
        sale.setTotalPrice(new BigDecimal("35.99"));
    }

    // Test 1 — save with shopOwner succeeds
    @Test
    void save_WithShopOwner_ShouldSucceed() {
        when(saleRepository.save(any()))
                .thenReturn(sale);

        Sale result = saleService.save(sale);

        assertNotNull(result);
        assertEquals(1, result.getQuantity());
        verify(saleRepository, times(1))
                .save(any());
    }

    // Test 2 — save with company succeeds
    @Test
    void save_WithCompany_ShouldSucceed() {
        sale.setShopOwner(null);
        sale.setCompany(company);

        when(saleRepository.save(any()))
                .thenReturn(sale);

        Sale result = saleService.save(sale);

        assertNotNull(result);
        verify(saleRepository, times(1))
                .save(any());
    }

    // Test 3 — save with both throws exception
    @Test
    void save_WithBothShopOwnerAndCompany_ShouldThrow() {
        sale.setShopOwner(shopOwner);
        sale.setCompany(company);

        assertThrows(RuntimeException.class,
                () -> saleService.save(sale));

        verify(saleRepository, never()).save(any());
    }

    // Test 4 — save with neither throws exception
    @Test
    void save_WithNeitherShopOwnerNorCompany_ShouldThrow() {
        sale.setShopOwner(null);
        sale.setCompany(null);

        assertThrows(RuntimeException.class,
                () -> saleService.save(sale));

        verify(saleRepository, never()).save(any());
    }

    // Test 5 — findAll returns list
    @Test
    void findAll_ShouldReturnList() {
        when(saleRepository.findAll())
                .thenReturn(Arrays.asList(sale));

        List<Sale> result =
                saleService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // Test 6 — findById returns sale
    @Test
    void findById_ShouldReturnSale() {
        when(saleRepository.findById(1L))
                .thenReturn(Optional.of(sale));

        Optional<Sale> result =
                saleService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1,
                result.get().getQuantity());
    }

    // Test 7 — findByPersonId returns list
    @Test
    void findByPersonId_ShouldReturnList() {
        when(saleRepository.findByPersonId(1L))
                .thenReturn(Arrays.asList(sale));

        List<Sale> result =
                saleService.findByPersonId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // Test 8 — findByShopOwnerId returns list
    @Test
    void findByShopOwnerId_ShouldReturnList() {
        when(saleRepository
                .findByShopOwnerId(1L))
                .thenReturn(Arrays.asList(sale));

        List<Sale> result =
                saleService.findByShopOwnerId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // Test 9 — findByCompanyId returns list
    @Test
    void findByCompanyId_ShouldReturnList() {
        when(saleRepository
                .findByCompanyId(1L))
                .thenReturn(Arrays.asList(sale));

        List<Sale> result =
                saleService.findByCompanyId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // Test 10 — update returns updated sale
    @Test
    void update_ShouldReturnUpdatedSale() {
        Sale updated = new Sale();
        updated.setQuantity(2);
        updated.setUnitPrice(
                new BigDecimal("35.99"));
        updated.setShopOwner(shopOwner);

        when(saleRepository.findById(1L))
                .thenReturn(Optional.of(sale));
        when(saleRepository.save(any()))
                .thenReturn(sale);

        Sale result =
                saleService.update(1L, updated);

        assertNotNull(result);
        verify(saleRepository, times(1))
                .save(any());
    }

    // Test 11 — update throws when not found
    @Test
    void update_ShouldThrow_WhenNotFound() {
        when(saleRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> saleService.update(
                        99L, sale));
    }

    // Test 12 — deleteById calls repository
    @Test
    void deleteById_ShouldDelete() {
        when(saleRepository.existsById(1L))
                .thenReturn(true);
        doNothing().when(saleRepository)
                .deleteById(1L);

        saleService.deleteById(1L);

        verify(saleRepository, times(1))
                .deleteById(1L);
    }

    // Test 13 — deleteById throws when not found
    @Test
    void deleteById_ShouldThrow_WhenNotFound() {
        when(saleRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> saleService.deleteById(99L));
    }
}