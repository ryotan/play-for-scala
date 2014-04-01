# --- !Ups

create table Users (
  email VARCHAR(1000) PRIMARY KEY,
  password VARCHAR(1000) NOT NULL,
  fullname VARCHAR(255) NOT NULL,
  is_admin CHAR(1) NOT NULL
)

# --- !Downs

drop table Users;
