# --- !Ups
alter table Users alter column id rename to user_id;
alter table Posts alter column id rename to post_id;

# --- !Downs
alter table Users alter column user_id rename to id;
alter table Posts alter column post_id rename to id;
