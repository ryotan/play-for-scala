# --- !Ups

CREATE TABLE PRODUCTS (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  ean         BIGINT         NOT NULL,
  name        VARCHAR(255)   NOT NULL,
  description VARCHAR(10000) NOT NULL
);

# --- !Downs

DROP TABLE PRODUCTS;
