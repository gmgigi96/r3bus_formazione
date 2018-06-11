drop table if exists utenti;

create table utenti
(
	username	varchar(20),
	pw			varchar(100),
	auth		varchar(20),
	
	primary key(username),
	unique (username, auth)
);

alter table public.utenti
    owner to formazione;

insert into utenti(username, pw, auth) values('responsabile', '$2a$10$KM3MUBmfE8vtl6mar5LEVOHXn1Z9A3PQ0AJmKE9AmZU4EdIaq4yji', 'RESPONSABILE');
insert into utenti(username, pw, auth) values('organizzatore', '$2a$10$ZQ1Wn4Nc.gvz5KLShA9VoOHjK/c64A4OE3ReQeSvVaTD88fXPcJ5G', 'ORGANIZZATORE');
insert into utenti(username, pw, auth) values('direttore', '$2a$10$mI4/HC5pvJQsOd9JG7GmU.0bEYSv5gd//sUfJSNmxIgJTYBgKrB9G', 'DIRETTORE');
insert into utenti(username, pw, auth) values('omar', '$2a$10$mI4/HC5pvJQsOd9JG7GmU.0bEYSv5gd//sUfJSNmxIgJTYBgKrB9G', 'DIRETTORE');
