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

insert into utenti(username, pw, auth) values('centro-1', '$2y$12$0sAyK3xWVY9CX.Z3YgayJe7WAogoLqDcCvw7SEGMBvuFahYCG7KGi', 'RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-2', '$2y$12$WTjyc1mLtkajVHwSb6kD8evIDL4jlt7y8DoC0AP4KdkxT5OAr0fdq', 'RESPONSABILE');
insert into utenti(username, pw, auth) values('organizzatore', '$2a$10$ZQ1Wn4Nc.gvz5KLShA9VoOHjK/c64A4OE3ReQeSvVaTD88fXPcJ5G', 'ORGANIZZATORE');
insert into utenti(username, pw, auth) values('direttore', '$2a$10$mI4/HC5pvJQsOd9JG7GmU.0bEYSv5gd//sUfJSNmxIgJTYBgKrB9G', 'DIRETTORE');
