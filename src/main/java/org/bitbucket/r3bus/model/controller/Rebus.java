package org.bitbucket.r3bus.model.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
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

	public Rebus() {
		statisticheController = new StatisticheController();
	}

	// gestione allievo

	public boolean gestisciAllievo(String codiceFiscale) {
		this.allievoCorrente = azienda.getAllievo(codiceFiscale);
		return this.allievoCorrente != null;
	}

	public void aggiungiAllievo(Allievo allievo) {
		this.azienda.salvaAllievo(allievo);
	}

	public void terminaGestioneAllievo() {
		this.allievoCorrente = null;
	}

	public void prenotaAttivita(List<Long> codiciAttivita) {
		for (Long id : codiciAttivita) {
			Attivita attivita = this.centroGestito.getAttivita(id);
			this.allievoCorrente.prenotaAttivita(attivita);
		}
		this.allievoCorrente = this.azienda.salvaAllievo(this.allievoCorrente);
	}

	public void annullaPrenotazione(List<Long> codiciAttivita) {
		for (Long id : codiciAttivita) {
			Attivita attivita = centroGestito.getAttivita(id);
			allievoCorrente.annullaPrenotazione(attivita);
		}
		this.allievoCorrente = this.azienda.salvaAllievo(this.allievoCorrente);
	}

	public void eliminaAllievo() {
		azienda.eliminaAllievo(this.allievoCorrente);
		this.allievoCorrente = null;
	}

	public Map<String, Set<Attivita>> getAttivitaPrenotateAllievo(Allievo allievo) {
		return this.azienda.getAttivitaPrenotateAllievo(allievo);
	}

	// gestione attività

	public void creaNuovaAttivita(Long codiceCentro, Attivita attivita) {
		Centro c = azienda.getCentro(codiceCentro);
		c.addAttivita(attivita);
		this.azienda.salvaCentro(c);
	}

	public void modificaAttivita(Long codiceCentro, Long codiceAttivita, Attivita attivita) {
		Centro c = azienda.getCentro(codiceCentro);
		Attivita a = c.getAttivita(codiceAttivita);
		a.aggiornaParametri(attivita.getNome(), attivita.getOrarioInizio(), attivita.getOrarioFine());
		this.azienda.salvaCentro(c);
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
		Set<Attivita> attivitaAllievoInCentro = new HashSet<>();
		List<Attivita> attivitaCentro = this.centroGestito.getAttivita();

		this.allievoCorrente.getAttivitaPrenotate().forEach((att) -> {
			if (attivitaCentro.contains(att)) {
				attivitaAllievoInCentro.add(att);
			}
		});

		return attivitaAllievoInCentro;
	}

	public boolean allievoInGestione() {
		return this.allievoCorrente != null;
	}

	public Attivita getAttivita(Long centro, Long id) {
		return this.azienda.getCentro(centro).getAttivita(id);
	}
}
