-- COUNTRIES
INSERT INTO country (id, version, name, sign, created, updated)
VALUES (1, 0, 'United States', 'US', NOW(), NOW());

INSERT INTO country (id, version, name, sign, created, updated)
VALUES (2, 0, 'Albania', 'AL', NOW(), NOW());

INSERT INTO country (id, version, name, sign, created, updated)
VALUES (3, 0, 'Hungary', 'HU', NOW(), NOW());

INSERT INTO country (id, version, name, sign, created, updated)
VALUES (4, 0, 'France', 'FR', NOW(), NOW());

-- COMPANIES (OneToOne → each gets a DIFFERENT country)
INSERT INTO company (id, version, name, tax_id, address, country_id, created, updated)
VALUES (1, 0, 'ORS Cosmetics', 'TAX001', '123 Beauty Ave, Chicago', 1, NOW(), NOW());

INSERT INTO company (id, version, name, tax_id, address, country_id, created, updated)
VALUES (2, 0, 'Loreal Paris', 'TAX002', '456 Fashion St, Paris', 4, NOW(), NOW());

-- SHOP OWNERS (ManyToOne → Country)
INSERT INTO shop_owner (id, version, shop_name, owner_name, address, country_id, created, updated)
VALUES (1, 0, 'Vivi Beauty Shop', 'Vivian Murathimi', '10 Main St, Tirana', 2, NOW(), NOW());

INSERT INTO shop_owner (id, version, shop_name, owner_name, address, country_id, created, updated)
VALUES (2, 0, 'Glamour Hub', 'Sara Smith', '20 King St, Budapest', 3, NOW(), NOW());

-- PERSONS (ManyToOne → Country)
INSERT INTO person (id, version, name, personal_id, address, country_id, created, updated)
VALUES (1, 0, 'John Doe', 'PID001', '5 Rose St, Tirana', 2, NOW(), NOW());

INSERT INTO person (id, version, name, personal_id, address, country_id, created, updated)
VALUES (2, 0, 'Jane Smith', 'PID002', '8 Oak Ave, Budapest', 3, NOW(), NOW());

-- PRODUCTS (ManyToOne → Company)
INSERT INTO product (id, version, name, description, price, company_id, created, updated)
VALUES (1, 0, 'Hair Relaxer', 'Professional hair relaxer kit', 29.99, 1, NOW(), NOW());

INSERT INTO product (id, version, name, description, price, company_id, created, updated)
VALUES (2, 0, 'Shampoo', 'Moisturizing shampoo 500ml', 12.99, 1, NOW(), NOW());

INSERT INTO product (id, version, name, description, price, company_id, created, updated)
VALUES (3, 0, 'Hair Dye', 'Permanent hair color', 19.99, 2, NOW(), NOW());

-- SHOP OWNER INVENTORY (ManyToMany)
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (1, 1);
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (1, 2);
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (2, 2);
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (2, 3);

-- PURCHASES (ShopOwner buys from Company)
INSERT INTO purchase (id, version, shop_owner_id, product_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (1, 0, 1, 1, 1, 50, 29.99, 1499.50, NOW(), NOW());

INSERT INTO purchase (id, version, shop_owner_id, product_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (2, 0, 1, 2, 1, 100, 12.99, 1299.00, NOW(), NOW());

-- SALES
-- Person buys from ShopOwner
INSERT INTO sale (id, version, person_id, product_id, shop_owner_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (1, 0, 1, 1, 1, NULL, 1, 35.99, 35.99, NOW(), NOW());

-- Person buys directly from Company
INSERT INTO sale (id, version, person_id, product_id, shop_owner_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (2, 0, 2, 3, NULL, 2, 1, 19.99, 19.99, NOW(), NOW());