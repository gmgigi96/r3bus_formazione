package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;

import org.bitbucket.r3bus.model.controller.AttivitaController;
import org.bitbucket.r3bus.model.controller.StatisticheController;

import lombok.Data;

@Data
public class Rebus {

	private Allievo allievoCorrente;
	private Azienda azienda;
	private Centro centroGestito;
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
		StatisticheController.getInstance().addCentro(c);
	}
	
	public void setIntervalloTemporale(LocalDateTime da, LocalDateTime a) {
		StatisticheController.getInstance().setIntervalloTemporale(da, a);
	}
	
	public Map<Centro, Set<Attivita>> getStatistiche() {
		return StatisticheController.getInstance().getStatistiche();
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
