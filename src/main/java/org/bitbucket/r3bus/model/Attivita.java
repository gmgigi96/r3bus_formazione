package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

@Data
public class Attivita {

	private static final AtomicInteger counter = new AtomicInteger(1);
	
	private int codice;
	private String nome;
	private LocalDateTime orarioInizio;
	private LocalDateTime orarioFine;

	private final Set<Allievo> allieviPrenotati;

	public Attivita() {
		allieviPrenotati = new TreeSet<>();
		this.codice = counter.incrementAndGet();  // TODO: change this when using a db
	}

	public Attivita(String nome, LocalDateTime inizio, LocalDateTime fine) {
		this();
		this.aggiornaParametri(nome, inizio, fine);
	}

	public void prenota(Allievo allievo) {
		allieviPrenotati.add(allievo);
	}

	public void annullaPrenotazione(Allievo allievo) {
		allieviPrenotati.remove(allievo);
	}

	public void aggiornaParametri(String nuovoNome, LocalDateTime nuovoInizio, LocalDateTime nuovaFine) {
		this.nome         = nuovoNome;
		this.orarioInizio = nuovoInizio;
		this.orarioFine   = nuovaFine;
	}
	
	public int getNumeroAllieviPrenotati() {
		return this.allieviPrenotati.size();
	}
}
