#---!Ups

CREATE TABLE Comments (
  comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  author     VARCHAR(255) NOT NULL,
  posted_at  TIMESTAMP    NOT NULL,
  content    NCLOB        NOT NULL,
  posted_on  BIGINT       NOT NULL REFERENCES Posts (post_id)
)

  #---!Downs

DROP TABLE Comments;
