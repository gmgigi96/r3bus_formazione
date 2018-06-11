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

insert into utenti(username, pw, auth) values('centro-1', '$2a$04$ANz4UysPg8IIb17IsNvnpuZsf1ihHpKVMofXI4iaEXLZz8HwuxsIK', 'RESPONSABILE');
insert into utenti(username, pw, auth) values('centro-2', '$2a$04$KQd5yXXhnrPo6AEvqMd1x.jbqNIoamTKT3VzXgd4cqiVO5ICT.QOK', 'RESPONSABILE');
insert into utenti(username, pw, auth) values('organizzatore', '$2a$10$ZQ1Wn4Nc.gvz5KLShA9VoOHjK/c64A4OE3ReQeSvVaTD88fXPcJ5G', 'ORGANIZZATORE');
insert into utenti(username, pw, auth) values('direttore', '$2a$10$mI4/HC5pvJQsOd9JG7GmU.0bEYSv5gd//sUfJSNmxIgJTYBgKrB9G', 'DIRETTORE');
