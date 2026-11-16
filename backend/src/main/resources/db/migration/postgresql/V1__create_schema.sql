-- Ανεξάρτητες ακολουθίες IDs, όπως ορίζονται στα Java entities.

CREATE SEQUENCE accounts_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE store_categories_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_categories_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE stores_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE products_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE orders_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE items_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE accounts (
                          id BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
                          username VARCHAR(20) NOT NULL,
                          password VARCHAR(30) NOT NULL,
                          email VARCHAR(30) NOT NULL,
                          phonenumber VARCHAR(13) NOT NULL,
                          age INTEGER NOT NULL,
                          address VARCHAR(30) NOT NULL,
                          city VARCHAR(30) NOT NULL,

                          CONSTRAINT uk_accounts_email UNIQUE (email),
                          CONSTRAINT uk_accounts_phone UNIQUE (phonenumber),
                          CONSTRAINT ck_accounts_age CHECK (age BETWEEN 18 AND 100)
);


CREATE TABLE store_categories (
                                  id BIGINT PRIMARY KEY DEFAULT nextval('store_categories_seq'),
                                  categoryname VARCHAR(50) NOT NULL
);


CREATE TABLE product_categories (
                                    id BIGINT PRIMARY KEY DEFAULT nextval('product_categories_seq'),
                                    description VARCHAR(50) NOT NULL
);


CREATE TABLE stores (
                        id BIGINT PRIMARY KEY DEFAULT nextval('stores_seq'),
                        name VARCHAR(30) NOT NULL,
                        storecategory_id BIGINT NOT NULL,

                        CONSTRAINT fk_stores_category
                            FOREIGN KEY (storecategory_id)
                                REFERENCES store_categories (id)
);


CREATE TABLE products (
                          id BIGINT PRIMARY KEY DEFAULT nextval('products_seq'),
                          serial VARCHAR(30) NOT NULL,
                          productname VARCHAR(50) NOT NULL,
                          price NUMERIC(10, 2) NOT NULL,
                          productcategory_id BIGINT NOT NULL,
                          store_id BIGINT NOT NULL,

                          CONSTRAINT fk_products_category
                              FOREIGN KEY (productcategory_id)
                                  REFERENCES product_categories (id),

                          CONSTRAINT fk_products_store
                              FOREIGN KEY (store_id)
                                  REFERENCES stores (id)
);


CREATE TABLE orders (
                        id BIGINT PRIMARY KEY DEFAULT nextval('orders_seq'),
                        account_id BIGINT NOT NULL,
                        store_id BIGINT NOT NULL,
                        submitdate TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
                        paymentmethod VARCHAR(13) NOT NULL,
                        cost NUMERIC(10, 2) NOT NULL,

                        CONSTRAINT fk_orders_account
                            FOREIGN KEY (account_id)
                                REFERENCES accounts (id),

                        CONSTRAINT fk_orders_store
                            FOREIGN KEY (store_id)
                                REFERENCES stores (id),

                        CONSTRAINT ck_orders_payment_method
                            CHECK (
                                paymentmethod IN (
                                                  'CASH',
                                                  'WIRE_TRANSFER',
                                                  'CREDIT_CARD'
                                    )
                                )
);


CREATE TABLE items (
                       id BIGINT PRIMARY KEY DEFAULT nextval('items_seq'),
                       product_id BIGINT NOT NULL,
                       quantity INTEGER NOT NULL,
                       price NUMERIC(10, 2) NOT NULL,
                       order_id BIGINT NOT NULL,

                       CONSTRAINT fk_items_product
                           FOREIGN KEY (product_id)
                               REFERENCES products (id),

                       CONSTRAINT fk_items_order
                           FOREIGN KEY (order_id)
                               REFERENCES orders (id)
);


CREATE INDEX idx_stores_category ON stores (storecategory_id);

CREATE INDEX idx_products_category ON products (productcategory_id);
CREATE INDEX idx_products_store ON products (store_id);

CREATE INDEX idx_orders_account ON orders (account_id);
CREATE INDEX idx_orders_store ON orders (store_id);

CREATE INDEX idx_items_product ON items (product_id);
CREATE INDEX idx_items_order ON items (order_id);