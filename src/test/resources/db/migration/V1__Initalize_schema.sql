-- ===========================
-- TABLAS PRINCIPALES
-- ===========================

CREATE TABLE books (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       isbn VARCHAR(13) UNIQUE NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       base_price DECIMAL(10, 2) NOT NULL,
                       discount_percentage DECIMAL(4, 2) DEFAULT 0
);



