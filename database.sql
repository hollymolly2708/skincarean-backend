CREATE DATABASE skincareMall;
show databases;

CREATE TABLE users(
    username VARCHAR(100) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    token VARCHAR(100) UNIQUE,
    token_expired_at BIGINT,
    token_created_at BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

SELECT * FROM users;
DELETE FROM users;
DESC users;
DROP TABLE users;

CREATE TABLE admins(
    username VARCHAR(100) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    is_admin BOOLEAN DEFAULT true,
    phone VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    token VARCHAR(100) UNIQUE,
    token_expired_at BIGINT,
    token_created_at BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE admins DROP COLUMN phone;
ALTER TABLE admins ADD COLUMN phone VARCHAR(100) NOT NULL;
SELECT * FROM admins;
DELETE FROM admins;
DESC admins;
DROP TABLE admins;

CREATE TABLE products (
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    thumbnail_image VARCHAR(255),
    added_by_admin VARCHAR(100),
    is_promo BOOLEAN DEFAULT FALSE NOT NULL,
    bpom_code VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (added_by_admin) REFERENCES admins(username) ON DELETE SET NULL
);

SELECT * FROM products;
DESC products;
DROP TABLE products;

CREATE TABLE product_variants(
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    product_id VARCHAR(100) NOT NULL,
    size VARCHAR(100) NOT NULL,
    price DECIMAL(20,2) NOT NULL,
    discount DECIMAL(4,2) NOT NULL,
    original_price DECIMAL(20,2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

SELECT * FROM product_variants;
DESC product_variants;
DROP TABLE product_variants;

CREATE TABLE product_images(
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    product_variant_id VARCHAR(100) NOT NULL,
    image_url VARCHAR(255),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id) ON DELETE CASCADE
);

SELECT * FROM product_images;
DESC product_images;
DROP TABLE product_images;

