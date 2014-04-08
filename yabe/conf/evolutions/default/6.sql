#---!Ups
insert into Users(email, password, fullname, is_admin) values ('1@example.com', 'psw', 'user name1', '0');
insert into Users(email, password, fullname, is_admin) values ('2@example.com', 'psw', 'user name2', '0');
insert into Users(email, password, fullname, is_admin) values ('3@example.com', 'psw', 'user name3', '0');
insert into Users(email, password, fullname, is_admin) values ('4@example.com', 'psw', 'user name4', '0');
insert into Users(email, password, fullname, is_admin) values ('5@example.com', 'psw', 'user name5', '0');

insert into Posts(title, content, author_id, posted_at) values('title1', 'content1
new line content', (select min(user_id) from Users), '2014-04-01');
insert into Posts(title, content, author_id, posted_at) values('title2', 'content2
new line content', (select min(user_id) from Users), '2014-04-02');
insert into Posts(title, content, author_id, posted_at) values('title3', 'content3
new line content', (select min(user_id) from Users), '2014-04-03');
insert into Posts(title, content, author_id, posted_at) values('title4', 'content4
new line content', (select min(user_id) from Users), '2014-04-04');
insert into Posts(title, content, author_id, posted_at) values('title5', 'content5
new line content', (select min(user_id) from Users), '2014-04-05');
insert into Posts(title, content, author_id, posted_at) values('title6', 'content6
new line content', (select min(user_id) from Users), '2014-04-06');
insert into Posts(title, content, author_id, posted_at) values('title7', 'content7
new line content', (select min(user_id) from Users), '2014-04-07');
insert into Posts(title, content, author_id, posted_at) values('title8', 'content8
new line content', (select min(user_id) from Users), '2014-04-08');
insert into Posts(title, content, author_id, posted_at) values('title9', 'content9
new line content', (select min(user_id) from Users), '2014-04-09');
insert into Posts(title, content, author_id, posted_at) values('title10', 'content10
new line content', (select min(user_id) from Users), '2014-04-10');
insert into Posts(title, content, author_id, posted_at) values('title11', 'content11
new line content', (select min(user_id) from Users), '2014-04-11');
insert into Posts(title, content, author_id, posted_at) values('title12', 'content12
new line content', (select min(user_id) from Users), '2014-04-12');

#---!Downs
delete from Comments;
delete from Posts;
delete from Users;
