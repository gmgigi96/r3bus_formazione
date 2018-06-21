R3busFormazione - Progetto SIW 2018
===================================

Questo repository contiene il progetto d'esame di tipo *avanzato* per l'appello di giugno 2018 del corso di Sistemi Informativi su Web, realizzato dagli studenti:

- Matteo Bernardini
- Gianmaria Del Monte
- Omar Elsayed


Consegna
--------

- La consegna del progetto è disponibile su [`/doc/assignment.pdf`][assignment].
- I casi d'uso sono disponibili su [`/doc/use_cases.md`][usecases]
- Il diagramma UML delle classi di progetto dello strato del dominio è disponibile su [`/doc/dcd.png`][dcd]
- Il diagramma UML dello schema del database è disponibile su [`/doc/db_schema.png`][db]


Esecuzione
----------

Il progetto è di tipo *Spring Boot* ed è gestito con *Maven*, per eseguirlo usare:

	mvn spring-boot:run

La connessione predefinita al database è:

	postgresql://formazione:formazione@localhost/formazione

In alternativa è possibile specificare una connessione personalizzata impostando la variabile d'ambiente `DATABASE_URL` all'url postgresql preferito.

Le tabelle vengono create automaticamente alla prima esecuzione (policy *update*) tranne la tabella `utenti` che contiene le credenziali e il ruolo per gli utenti registrati.

Nella directory `/init/` sono presenti degli script SQL di inizializzazione utili per popolare il database con dei valori d'esempio.


Autenticazione
--------------

Per gli utenti registrati con id e password i ruoli possibili sono *RESPONSABILE*, *ORGANIZZATORE* oppure *DIRETTORE*. Agli utenti autenticati tramite OAuth viene assegnato automaticamente il ruolo predefinito *ROLE_USER*. Non sono supportati più ruoli per lo stesso utente.

Per il ruolo di *RESPONSABILE*, ogni utente è riferito al rispettivo centro gestito e lo *username* è sempre nel formato `centro-n`, dove `n` è l'id del centro gestito (chiave primaria della tabella `centro`).

Per i ruoli di *ORGANIZZATORE* e *DIRETTORE* lo *username* può essere arbitrario.

Il ruolo predefinito *ROLE_USER* è dedicato agli allievi per lo svolgimento del caso d'uso *UC7*.

Per generare degli utenti registrati di esempio è possibile usare lo script `/init/users.sql`.


-----------------------------------------------------------
© 2018 Matteo Bernardini, Gianmaria Del Monte, Omar Elsayed



[assignment]: doc/assignment.pdf
[usecases]: doc/use_cases.md
[dcd]: doc/dcd.png
[db]: doc/db_schema.png