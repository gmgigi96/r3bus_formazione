package org.bitbucket.r3bus.model.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.service.AllievoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@Component
@SessionScope
public class Rebus {
	@Autowired
	private Azienda azienda;

	private Allievo allievoCorrente;
	private Centro centroGestito;
	private StatisticheController statisticheController;

	@Autowired
	private AllievoService allievoService;

	public Rebus() {
		statisticheController = new StatisticheController();
	}

	// gestione allievo

	public boolean gestisciAllievo(String codiceFiscale) {
		this.allievoCorrente = azienda.getAllievo(codiceFiscale);
		return this.allievoCorrente != null;
	}

	public void aggiungiAllievo(Allievo allievo) {
		this.azienda.addAllievo(allievo);
	}

	public void terminaGestioneAllievo() {
		this.allievoCorrente = null;
	}

	public void prenotaAttivita(List<Long> codiciAttivita) {
		for (Long id : codiciAttivita) {
			Attivita attivita = this.centroGestito.getAttivita(id);
			this.allievoCorrente.prenotaAttivita(attivita);
		}
		this.allievoService.save(this.allievoCorrente);
	}

	public void annullaPrenotazione(List<Long> codiciAttivita) {
		for (Long id : codiciAttivita) {
			Attivita attivita = centroGestito.getAttivita(id);
			allievoCorrente.annullaPrenotazione(attivita);
		}
		this.allievoService.save(this.allievoCorrente);
	}

	public void eliminaAllievo() {
		azienda.eliminaAllievo(this.allievoCorrente);
		this.allievoCorrente = null;
	}

	// gestione attività

	public void creaNuovaAttivita(Long codiceCentro, String nome, LocalDateTime inizio, LocalDateTime fine) {
		Centro c = azienda.getCentro(codiceCentro);
		c.addAttivita(nome, inizio, fine);
	}

	public void creaNuovaAttivita(Long codiceCentro, Attivita attivita) {
		Centro c = azienda.getCentro(codiceCentro);
		c.addAttivita(attivita);
	}

	public void modificaAttivita(Long codiceCentro, Long codiceAttivita, String nome, LocalDateTime inizio,
			LocalDateTime fine) {
		Centro c = azienda.getCentro(codiceCentro);
		Attivita a = c.getAttivita(codiceAttivita);
		a.aggiornaParametri(nome, inizio, fine);
	}
	
	public void modificaAttivita(Long codiceCentro, Long codiceAttivita, Attivita attivita) {
		Centro c = azienda.getCentro(codiceCentro);
		Attivita a = c.getAttivita(codiceAttivita);
		a.aggiornaParametri(attivita.getNome(), attivita.getOrarioInizio(), attivita.getOrarioFine());
	}

	public void setCentroGestito(Long codiceCentro) {
		Centro c = azienda.getCentro(codiceCentro);
		this.centroGestito = c;
	}

	// statistiche

	public List<Number> getNumeroAttivitaGiornaliere(Long codiceCentro, LocalDate inizio, LocalDate fine) {
		Centro c = azienda.getCentro(codiceCentro);
		return statisticheController.getNumeroAttivitaGiornaliere(c, inizio, fine);
	}

	public List<Number> getMediaPrenotati(Long codiceCentro, LocalDate inizio, LocalDate fine) {
		Centro c = azienda.getCentro(codiceCentro);
		return statisticheController.getMediaPrenotati(c, inizio, fine);
	}

	public Map<String, Number> getPrenotazioniPerAttivita(Long codiceCentro, LocalDate inizio, LocalDate fine) {
		Centro c = azienda.getCentro(codiceCentro);
		return statisticheController.getElencoAttivita(c, inizio, fine);
	}

	// interrogazioni

	public Set<Attivita> getAttivitaDisponibili() {
		return this.centroGestito.getAttivitaDisponibili();
	}

	public Set<Attivita> getAttivitaAllievo() {
		return this.allievoCorrente.getAttivitaPrenotate();
	}

	public boolean allievoInGestione() {
		return this.allievoCorrente != null;
	}

}
