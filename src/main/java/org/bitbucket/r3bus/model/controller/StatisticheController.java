package org.bitbucket.r3bus.model.controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.utils.LocalDateRange;

/**
 * La classe StatisticheController genera le statistiche
 */
public class StatisticheController {

	private LocalDate inizio;
	private LocalDate fine;
	private Centro centro;
	private Azienda azienda;

	public void setCentro(int codiceCentro) {
		this.centro = this.azienda.getCentro(codiceCentro);
	}

	public void setIntervallo(LocalDate inizio, LocalDate fine) {
		this.inizio = inizio;
		this.fine = fine;
	}

	public List<Long> getNumeroAttivitaGiornaliere() {
		List<Long> res = new LinkedList<>();
		for (LocalDate data : LocalDateRange.with(inizio, fine)) {
			res.add(centro.getNumeroAttivita(data));
		}
		return res;
	}

	public Map<String, Integer> getElencoAttivita() {
		Map<String, Integer> attivita2prenotati = new TreeMap<>();
		for (Attivita a : centro.getAttivitaInIntervallo(inizio, fine)) {
			attivita2prenotati.put(a.toString(), a.getNumeroAllieviPrenotati());
		}
		return attivita2prenotati;
	}

	public List<Float> getMediaPrenotati() {
		List<Float> res = new LinkedList<>();
		for (LocalDate data : LocalDateRange.with(inizio, fine)) {
			Set<Attivita> attivita = centro.getAttivitaInIntervallo(data, data);
			float mediaPrenotatiGiornaliera = attivita.stream().mapToInt(Attivita::getNumeroAllieviPrenotati).sum()
					/ (float) attivita.size();
			res.add(mediaPrenotatiGiornaliera / centro.getCapienza());
		}
		return res;
	}

}
