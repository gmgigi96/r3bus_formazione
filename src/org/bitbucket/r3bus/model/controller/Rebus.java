package org.bitbucket.r3bus.model.controller;

import java.time.LocalDateTime;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;

import lombok.Data;

@Data
public class Rebus {

	private Allievo allievoCorrente;
	private Azienda azienda;
	private Centro centroGestito;
	private StatisticheController statisticheController;
	private AttivitaController attivitaController;

	public void gestisciAllievo(String codiceFiscale) {
		this.allievoCorrente = azienda.getAllievo(codiceFiscale);
	}

	public void terminaGestione() {

	}

	public void prenotaAttivita(int codiceAttivita) {
		Attivita attivita = this.centroGestito.getAttivita(codiceAttivita);
		this.allievoCorrente.prenotaAttivita(attivita);
	}

	public void annullaPrenotazione(int codiceAttivita) {
		Attivita attivita = centroGestito.getAttivita(codiceAttivita);
		allievoCorrente.annullaPrenotazione(attivita);
	}
	
	public void eliminaAllievo() {
		azienda.eliminaAllievo(this.allievoCorrente);
		this.allievoCorrente = null;
	}
	
	public void addCentro(int codiceCentro) {
		Centro c = this.azienda.getCentro(codiceCentro);
		this.statisticheController.addCentro(c);
	}

	public void creaNuovaAttivita(String nome, LocalDateTime dataOra, int durata) {
		attivitaController.creaNuovaAttivita(nome,dataOra,durata, centroGestito);
	}
	
	public void gestisciAttivita(int codiceAttivita) {
		Attivita attivita = centroGestito.getAttivita(codiceAttivita);
		attivitaController.gestisciAttivita(attivita);
	}
	
	public void salvaAttivita(String nome, LocalDateTime dataOra, int durata) {
		attivitaController.salvaAttivita(nome, dataOra, durata);
	}
	
}
