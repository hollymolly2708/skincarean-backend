CREATE DATABASE skincarean;
USE skincarean;

DROP DATABASE skincarean;
CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    full_name VARCHAR(255),
    email VARCHAR(255),
    token VARCHAR(255),
    address VARCHAR(255),
    created_at TIMESTAMP,
    photo_profile VARCHAR(255),
    last_updated_at TIMESTAMP,
    token_expired_at BIGINT,
    token_created_at BIGINT,
    phone VARCHAR(20)
);

SELECT * FROM users;
CREATE TABLE product_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rating TINYINT,
    review TEXT,
    usage_period VARCHAR(100),
    is_recommended BOOLEAN,
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP,
    product_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);


CREATE TABLE product_variant_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255),
    product_variant_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);




CREATE TABLE products (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    thumbnail_image VARCHAR(255),
    bpom_code VARCHAR(255),
    is_promo BOOLEAN,
    stok BIGINT,
    is_popular_product BOOLEAN,
    created_at TIMESTAMP,
    ingredient TEXT,
    last_updated_at TIMESTAMP,
    added_by_admin VARCHAR(255),
    brand_id BIGINT,
    category_item_id BIGINT,
    FOREIGN KEY (added_by_admin) REFERENCES admins(username),
    FOREIGN KEY (brand_id) REFERENCES brands(id),
    FOREIGN KEY (category_item_id) REFERENCES category_items(id)
);

select * from products;

CREATE TABLE product_variants(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
product_id VARCHAR (255),
size VARCHAR(50),
price DECIMAL(20,2),
discount DECIMAL(20,2),
thumbnail_variant_image VARCHAR(255),
stok BIGINT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

select * from product_variants;

update product_variants set thumbnail_variant_image = "https://drive.google.com/uc?export=view&id=1jH_zpz-VHRIzVl0a66MtMmZmRgVnKVEN" WHERE id =67;

-- UPDATE product_variants pv
-- JOIN (
--     SELECT product_variant_id, MIN(image_url) AS image_url
--     FROM product_variant_images
--     GROUP BY product_variant_id
-- ) pi ON pv.id = pi.product_variant_id
-- SET pv.thumbnail_variant_image = pi.image_url;


-- UPDATE product_variants pv
-- JOIN products p ON pv.product_id = p.id
-- SET pv.original_price = p.original_price;

-- INSERT INTO product_variants(
-- product_id,
-- size,
-- price,
-- discount,
-- stok,
-- created_at,
-- last_updated_at)
-- SELECT
-- id as product_id,
-- COALESCE(size, 'default') AS size,
-- price,
-- discount,
-- stok,
-- created_at,
-- last_updated_at
-- FROM products;



CREATE TABLE payment_process (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_code VARCHAR(255),
    payment_status VARCHAR(255),
    paid_date TIMESTAMP,
    total_paid DECIMAL(20, 2),
    payment_method_id BIGINT,
    order_id VARCHAR(255),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    image VARCHAR(255),
    description TEXT
);


CREATE TABLE order_items (
    id VARCHAR(255) PRIMARY KEY,
    quantity BIGINT,
    total_price DECIMAL(20, 2),
    created_at TIMESTAMP,
    expired_at TIMESTAMP,
    product_id VARCHAR(255) NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);


CREATE TABLE orders (
    id VARCHAR(255) PRIMARY KEY,
    order_status VARCHAR(255),
    total_price DECIMAL(20, 2),
    shipping_address VARCHAR(255),
    shipping_cost DECIMAL(20, 2),
    description TEXT,
    tax DECIMAL(20, 2),
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);
DELETE FROM orders;
CREATE TABLE category_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    category_items_image VARCHAR(255),
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP,
    category_image VARCHAR(255)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    total DECIMAL(20, 2),
    is_active BOOLEAN,
    quantity BIGINT,
    cart_id BIGINT NOT NULL,
    product_variant_id BIGINT,
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE
);

DESC  cart_items;

CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(20, 2),
    quantity BIGINT,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);

DESC carts;

CREATE TABLE brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    brand_logo VARCHAR(255),
    brand_poster VARCHAR(255),
    website_media_url VARCHAR(255),
    instagram_url VARCHAR(255),
    facebook_url VARCHAR(255),
    contact_email_url VARCHAR(255),
    address VARCHAR(255),
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP,
    is_top_brand BOOLEAN
);

CREATE TABLE admins (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    address VARCHAR(255),
    is_admin BOOLEAN,
    email VARCHAR(255),
    phone VARCHAR(20),
    token VARCHAR(255),
    photo_profile VARCHAR(255),
    token_expired_at BIGINT,
    token_created_at BIGINT,
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP
);


SET SQL_SAFE_UPDATES = 0;
