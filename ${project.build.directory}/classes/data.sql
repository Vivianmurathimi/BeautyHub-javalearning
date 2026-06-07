-- COUNTRIES
INSERT INTO country (version, name, sign, created, updated)
VALUES (0, 'United States', 'US', NOW(), NOW());

INSERT INTO country (version, name, sign, created, updated)
VALUES (0, 'Albania', 'AL', NOW(), NOW());

INSERT INTO country (version, name, sign, created, updated)
VALUES (0, 'Hungary', 'HU', NOW(), NOW());

INSERT INTO country (version, name, sign, created, updated)
VALUES (0, 'France', 'FR', NOW(), NOW());

-- Reset sequence after inserts
ALTER TABLE country ALTER COLUMN id RESTART WITH 5;

-- COMPANIES
INSERT INTO company (version, name, tax_id, address, country_id, created, updated)
VALUES (0, 'ORS Cosmetics', 'TAX001', '123 Beauty Ave', 1, NOW(), NOW());

INSERT INTO company (version, name, tax_id, address, country_id, created, updated)
VALUES (0, 'Loreal Paris', 'TAX002', '456 Fashion St', 4, NOW(), NOW());

ALTER TABLE company ALTER COLUMN id RESTART WITH 3;

-- SHOP OWNERS
INSERT INTO shop_owner (version, shop_name, owner_name, address, country_id, created, updated)
VALUES (0, 'Vivi Beauty Shop', 'Vivian Murathimi', '10 Main St', 2, NOW(), NOW());

INSERT INTO shop_owner (version, shop_name, owner_name, address, country_id, created, updated)
VALUES (0, 'Glamour Hub', 'Sara Smith', '20 King St', 3, NOW(), NOW());

ALTER TABLE shop_owner ALTER COLUMN id RESTART WITH 3;

-- PERSONS
INSERT INTO person (version, name, personal_id, address, country_id, created, updated)
VALUES (0, 'John Doe', 'PID001', '5 Rose St', 2, NOW(), NOW());

INSERT INTO person (version, name, personal_id, address, country_id, created, updated)
VALUES (0, 'Jane Smith', 'PID002', '8 Oak Ave', 3, NOW(), NOW());

ALTER TABLE person ALTER COLUMN id RESTART WITH 3;

-- PRODUCTS
INSERT INTO product (version, name, description, price, company_id, created, updated)
VALUES (0, 'Hair Relaxer', 'Professional hair relaxer kit', 29.99, 1, NOW(), NOW());

INSERT INTO product (version, name, description, price, company_id, created, updated)
VALUES (0, 'Shampoo', 'Moisturizing shampoo 500ml', 12.99, 1, NOW(), NOW());

INSERT INTO product (version, name, description, price, company_id, created, updated)
VALUES (0, 'Hair Dye', 'Permanent hair color', 19.99, 2, NOW(), NOW());

ALTER TABLE product ALTER COLUMN id RESTART WITH 4;

-- SHOP OWNER INVENTORY
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (1, 1);
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (1, 2);
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (2, 2);
INSERT INTO shop_owner_product (shop_owner_id, product_id)
VALUES (2, 3);

-- PURCHASES
INSERT INTO purchase (version, shop_owner_id, product_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (0, 1, 1, 1, 50, 29.99, 1499.50, NOW(), NOW());

ALTER TABLE purchase ALTER COLUMN id RESTART WITH 2;

-- SALES
INSERT INTO sale (version, person_id, product_id, shop_owner_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (0, 1, 1, 1, NULL, 1, 35.99, 35.99, NOW(), NOW());

INSERT INTO sale (version, person_id, product_id, shop_owner_id, company_id, quantity, unit_price, total_price, created, updated)
VALUES (0, 2, 3, NULL, 2, 1, 19.99, 19.99, NOW(), NOW());

ALTER TABLE sale ALTER COLUMN id RESTART WITH 3;