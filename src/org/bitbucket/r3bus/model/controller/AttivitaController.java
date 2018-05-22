package org.bitbucket.r3bus.model.controller;

import java.time.LocalDateTime;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;

public class AttivitaController {
	
	private Attivita attivitaCorrente;
		
	public void creaNuovaAttivita(String nome, LocalDateTime dataOra, int durata, Centro c) {
		c.addAttivita(nome, dataOra, durata);
	}

	public void gestisciAttivita(Attivita attivita) {
		this.attivitaCorrente = attivita;
	}

	public void salvaAttivita(String nome, LocalDateTime dataOra, int durata) {
		this.attivitaCorrente.setNome(nome);
		this.attivitaCorrente.setDataOra(dataOra);
		this.attivitaCorrente.setDurata(durata);
		this.attivitaCorrente = null;
	}
}
