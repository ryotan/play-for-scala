# --- !Ups

CREATE TABLE WAREHOUSE (
  id   BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE STOCK_ITEM (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id   BIGINT NOT NULL,
  warehouse_id BIGINT NOT NULL REFERENCES WAREHOUSE (id),
  quantity     BIGINT DEFAULT 0
);

# --- !Downs

DROP TABLE STOCK_ITEM;
DROP TABLE WAREHOUSE;
