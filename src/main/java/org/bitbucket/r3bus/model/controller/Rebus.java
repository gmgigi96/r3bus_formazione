package org.bitbucket.r3bus.model.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

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
		centroGestito.addAttivita(nome, dataOra, durata);
	}
	
	public void modificaAttivita(int codiceAttivita, String nome, LocalDateTime dataOra, int durata) {
		Attivita a = centroGestito.getAttivita(codiceAttivita);
		a.aggiornaParametri(nome, dataOra, durata);
	}
	
	
}
