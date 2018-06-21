/*  use this command:
 *   psql -a -W -f users.sql -U <user> -d <database> -h <host>
 *  
 *  for example, when building locally:
 *   psql -a -W -f users.sql -U formazione -d formazione -h localhost
 */

drop table if exists utenti;

create table utenti
(
	username	varchar(20),
	pw			varchar(100),
	auth		varchar(20),
	primary key(username)
);

insert into utenti(username, pw, auth) values('centro-1','$2a$10$9nH0jYY2Lz1D7qz6GGK9S.ZC/XpWeBnGmpq/khqimPTcB4CERop.6','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-2','$2a$10$VlAisOlmpn8iZ6zbHSnJSuYQ3vqx05ev2SowO.o2Ln4hmynWTnS2W','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-3','$2a$10$L7RBH3jZ5DnVWRRNBxP01eKSSpE4V48zb3u6ZIhJmjiyPGtazpkyG','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-4','$2a$10$bk207aL5v9w2NmS0lQiyHOb8pP.R.dMD8ChH9mio0pMd0rA.RsKgS','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-5','$2a$10$QX4Q/KDvosQQstapQOenE.DcJ66/dBUQyhfzsjsEXdJbPVDpiMbbq','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-6','$2a$10$.UyzvU1MjRbjq0kUrE2Q2.dF5zHHt2Hhe/3DzzqP1jChIdLjIzkSq','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-7','$2a$10$bv4/wx2jogOItvTApJjkuuiZkzBZbvBIuiKiPz0ZCpo4RiIVdFXoW','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-8','$2a$10$E5B5YFu5dRfYqqtvKfKiDO5GMw853A5Qs7zcdB1fBDpZHHjNz/6aK','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-9','$2a$10$Dg2ikZpUd5wANHy1hfkcqODUAVrQcgLBBnqehJeJtvGbfJVioVGp2','RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-10','$2a$10$hBTiN5BDlugA9Ha5XyubEuttNrWmGh6QuUw.XvCYAh46F9u7bsmwq','RESPONSABILE');


insert into utenti(username, pw, auth) values('organizzatore', '$2a$10$ZQ1Wn4Nc.gvz5KLShA9VoOHjK/c64A4OE3ReQeSvVaTD88fXPcJ5G', 'ORGANIZZATORE');
insert into utenti(username, pw, auth) values('direttore', '$2a$10$mI4/HC5pvJQsOd9JG7GmU.0bEYSv5gd//sUfJSNmxIgJTYBgKrB9G', 'DIRETTORE');
