package org.bitbucket.r3bus.model.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;

/**
 * La classe StatisticheController genera le statistiche
 */
public class StatisticheController {

	private LocalDateTime intervalloTemporaleDa;
	private LocalDateTime intervalloTemporaleA;

	private final Set<Centro> centri;

	private StatisticheController() {
		centri = new TreeSet<>();
	}

	public static StatisticheController statisticheController;

	public static StatisticheController getInstance() {
		if (statisticheController == null) {
			statisticheController = new StatisticheController();
		}
		return statisticheController;
	}

	public void setIntervalloTemporale(LocalDateTime da, LocalDateTime a) {
		this.intervalloTemporaleDa = da;
		this.intervalloTemporaleA = a;
	}

	public void addCentro(Centro centro) {
		this.centri.add(centro);
	}

	public Map<Centro,Set<Attivita>> getStatistiche() {
		Map<Centro, Set<Attivita>> centro2Attivita = new HashMap<>();

		for (Centro c : centri) {
			Set<Attivita> attivita = new HashSet<>();
			attivita.addAll(c.getAttivitaInIntervallo(intervalloTemporaleDa, intervalloTemporaleA));
			centro2Attivita.put(c, attivita);
		}

		return centro2Attivita;
	}

	public void reset() {
		this.intervalloTemporaleA = null;
		this.intervalloTemporaleA = null;
		this.centri.clear();
	}

}
