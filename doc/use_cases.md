Casi d'uso - R3busFormazione
----------------------------

UC1
================================================
***Prenotazione partecipazione ad un'attività***

Att. primario: Responsabile del centro  
Att. finale:   Allievo

1. Un allievo si presenta al bancone del centro di formazione per iscriversi a delle attività.
2. L'allievo fornisce le proprie credenziali (cod. fiscale).
3. Il responsabile verifica se le credenziali corrispondono ad un allievo già registrato.
4. Il responsabile elenca le attività disponibili.
5. L'allievo indica le attività alle quali intende partecipare.
6. Il responsabile registra le attività scelte.
7. L'allievo va via con il promemoria delle attività prenotate presso il centro.

### Estensioni:
- 3a. L'allievo non è registrato.
	1. L'allievo fornisce i suoi dati anagrafici.
	2. Il responsabile registra i dati.
- 5a. L'allievo non è interessato a nessuna attività.
	1. La prenotazione viene annullata.


UC2
==========================
***Annulla prenotazione***

Att. primario: Responsabile del centro  
Att. finale:   Allievo

1. Un allievo si presenta al bancone del centro di formazione per annullare delle partecipazioni ad attività.
2. L'allievo fornisce le proprie credenziali (cod. fiscale).
3. Il responsabile verifica se le credenziali corrispondono ad un allievo già registrato.
4. Il responsabile elenca le attività prenotate dall'allievo.
5. L'allievo indica le prenotazioni che vuole annullare.
6. Il responsabile effettua l'operazione.
7. L'allievo va via con il promemoria delle attività di quel centro alle quali si è prenotato.


UC3
=====================================
***Eliminazione iscrizione allievo***

Att. primario: Responsabile del centro  
Att. finale:   Allievo

1. Un allievo si presenta al bancone del centro di formazione per disiscriversi dall'azienda.
2. L'allievo fornisce le proprie credenziali (cod. fiscale).
3. Il responsabile verifica se le credenziali corrispondono ad un allievo già registrato.
4. Il responsabile elimina i dati dell'allievo e le relative prenotazioni.
5. Il sistema mostra un messaggio di conferma.
6. L'allievo va via dal bancone.


UC4
========================================
***Visualizzazione statistiche centro***

Attore principale: Direttore dell'azienda

1. Il direttore accede al portale per visualizzare le statistiche di un centro.
2. Il direttore indica il centro e l'intervallo temporale di interesse.
3. Il sistema mostra le statistiche richieste.


UC5
==========================================
***Inserimento nuova attività formativa***

Attore principale: Organizzatore dell'attività

1. L'organizzatore accede al portale per creare una nuova attività presso un centro.
2. L'organizzatore sceglie il centro in cui si effettuerà l'attività.
3. L'organizzatore inserisce i dati dell'attività formativa.
4. Il sistema registra l'attività.


UC6
======================================
***Modifica dati attività formativa***

Attore principale: Organizzatore dell'attività

1. L'organizzatore accede al portale per modificare i dati di un'attività esistente presso un centro.
2. L'organizzatore sceglie il centro in cui si svolge l'attività.
3. Il sistema elenca le attività create dall'organizzatore.
4. L'organizzatore indica l'attività da modificare.
5. L'organizzatore effettua le dovute modifiche.
6. Il sistema verifica la validità dei dati inseriti.
7. Il sistema invia una email a tutti i partecipanti dell'attività indicando le modifiche avvenute.

### Estensioni:
- 3a. I dati inseriti non sono validi.
	1. Il sistema notifica l'organizzatore.
- 4a. L'organizzatore annulla le modifiche.
	1. Il sistema annulla la fase di modifica.


UC7
========================================
***Visualizzazione attività prenotate***

Attore principale: Allievo

1. L'allievo accede al portale per visualizzare le attività di tutti i centri alle quali si è prenotato.
2. L'allievo si autentica tramite OAuth fornendo il proprio indirizzo e-mail.
3. Il sistema verifica che l'e-mail sia associata ad un allievo registrato.
4. Il sistema mostra l'elenco delle attività prenotate presso ogni centro.

### Estensioni:
- 2a. L'indirizzo e-mail non è associato a nessun allievo registrato
	1. L'autenticazione fallisce e il sistema mostra un messaggio informativo di fallimento all'allievo.


-----------------------------------------------------------
© 2018 Matteo Bernardini, Gianmaria Del Monte, Omar Elsayed
