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

	// gestione allievo

	public void gestisciAllievo(String codiceFiscale) {
		this.allievoCorrente = azienda.getAllievo(codiceFiscale);
	}

	public void terminaGestioneAllievo() {
		this.allievoCorrente = null;
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

	// gestione attività

	public void creaNuovaAttivita(int codiceCentro, String nome, LocalDateTime inizio, LocalDateTime fine) {
		Centro c = azienda.getCentro(codiceCentro);
		c.addAttivita(nome, inizio, fine);
	}

	// TOFIX: il "centroGestito" non ha nulla a che vedere con questo caso d'uso
	public void modificaAttivita(int codiceAttivita, String nome, LocalDateTime inizio, LocalDateTime fine) {
		Attivita a = centroGestito.getAttivita(codiceAttivita);
		a.aggiornaParametri(nome, inizio, fine);
	}
	
}
