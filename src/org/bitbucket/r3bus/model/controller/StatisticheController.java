package org.bitbucket.r3bus.model.controller;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;

public class StatisticheController {
	
	private LocalDateTime intervalloTemporaleDa;
	private LocalDateTime intervalloTemporaleA;
	
	private final Set<Centro> centri;
	
	public StatisticheController() {
		centri = new TreeSet<>();
	}
	
	public void setIntervalloTemporale(LocalDateTime da, LocalDateTime a) {
		this.intervalloTemporaleDa = da;
		this.intervalloTemporaleA = a;
	}
	
	public void addCentro(Centro centro) {
		this.centri.add(centro);
	}
	
	public Set<Attivita> getStatistiche() {
		Set<Attivita> attivita = new TreeSet<>();
		for (Centro c : centri) {
			attivita.addAll(c.getAttivitaInIntervallo(intervalloTemporaleDa, intervalloTemporaleA));
		}
		return attivita;
	}
	
}
