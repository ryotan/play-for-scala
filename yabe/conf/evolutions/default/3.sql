# --- !Ups

CREATE TABLE Posts (
  id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  title     VARCHAR(255) NOT NULL,
  content   CLOB         NOT NULL,
  author_id BIGINT       NOT NULL REFERENCES Users (id),
  posted_at TIMESTAMP    NOT NULL
);

# --- !Downs
DROP TABLE Posts;
