package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

@Data
public class Attivita {

	private int codice;
	private String nome;
	private LocalDateTime orarioInizio;
	private LocalDateTime orarioFine;

	private final Set<Allievo> allieviPrenotati;

	public Attivita() {
		allieviPrenotati = new TreeSet<>();
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
}
