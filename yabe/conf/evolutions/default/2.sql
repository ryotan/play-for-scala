# ---!Ups
alter table Users drop primary key;
alter table Users alter column email set NOT NULL;
alter table Users add id BIGINT AUTO_INCREMENT;
alter table Users add PRIMARY KEY(id);

# ---!Downs
alter table Users drop column id;
alter table Users add primary key(email);
