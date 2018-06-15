insert into centro(id, nome, capienza) values('1','centro-1', '100');
insert into centro(id, nome, capienza) values('2','centro-2', '10');

insert into attivita(id, nome, orario_inizio, orario_fine, centro_id) 
		values('3','Come coccolare un fenicottero','2018-08-02 12:00:00','2018-08-02 14:00:00','2');
insert into attivita(id, nome, orario_inizio, orario_fine, centro_id) 
		values('4','Osservazione intensa','2018-08-02 12:00:00','2018-08-02 14:00:00','1');
insert into attivita(id, nome, orario_inizio, orario_fine, centro_id) 
		values('5','Riposo alternato','2018-08-02 14:00:00','2018-08-02 16:00:00','1');
insert into attivita(id, nome, orario_inizio, orario_fine, centro_id) 
		values('6','Addestramento Piccioni 1','2018-08-02 12:00:00','2018-08-02 14:00:00','2');
insert into attivita(id, nome, orario_inizio, orario_fine, centro_id) 
		values('7','Addestramento Piccioni 2','2018-08-03 14:00:00','2018-08-03 16:00:00','2');
insert into attivita(id, nome, orario_inizio, orario_fine, centro_id) 
		values('8','Soccorso in mare','2018-08-12 9:00:00','2018-08-12 11:00:00','1');


insert into allievo(id, codice_fiscale, nome, cognome, email, luogo_nascita, data_nascita, telefono) 
	values('9', 'MRAJMP81MARIO', 'Mario','Jumpman','super.mario@nintendo.com','Mario Land','1981-08-03','0101010101');
insert into allievo(id, codice_fiscale, nome, cognome, email, luogo_nascita, data_nascita, telefono) 
	values('10','LGUJMP81MARIO', 'Luigi','Jumpman','super.luigi@nintendo.com','Mario Land','1981-08-03','1101010101');


insert into allievo_attivita_prenotate(attivita_prenotate_id,allievi_prenotati_id) values('4','9');
insert into allievo_attivita_prenotate(attivita_prenotate_id,allievi_prenotati_id) values('8','9');

ALTER SEQUENCE public.hibernate_sequence START WITH 11;

ALTER SEQUENCE public.hibernate_sequence RESTART;