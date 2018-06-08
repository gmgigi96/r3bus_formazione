package org.bitbucket.r3bus.model.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class Rebus {

	private Allievo allievoCorrente;
	private Azienda azienda;
	private Centro centroGestito;
	private StatisticheController statisticheController;

	public Rebus() {
		azienda = new Azienda();
		statisticheController = new StatisticheController();
	}
	
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

	public void modificaAttivita(int codiceCentro, int codiceAttivita, String nome, LocalDateTime inizio, LocalDateTime fine) {
		Centro c = azienda.getCentro(codiceCentro);
		Attivita a = c.getAttivita(codiceAttivita);
		a.aggiornaParametri(nome, inizio, fine);
	}

	public void setCentroGestito(int codiceCentro) {
		Centro c = azienda.getCentro(codiceCentro);
		this.centroGestito = c;
	}

	//per i grafici
	
	public List<Number> getNumeroAttivitaGiornaliere(int codiceCentro, LocalDate inizio, LocalDate fine) {
		Centro c = azienda.getCentro(codiceCentro);
		return statisticheController.getNumeroAttivitaGiornaliere(c, inizio, fine);
	}

	public List<Number> getMediaPrenotati(int codiceCentro, LocalDate inizio, LocalDate fine) {
		Centro c = azienda.getCentro(codiceCentro);
		return statisticheController.getMediaPrenotati(c, inizio, fine);
	}

}
