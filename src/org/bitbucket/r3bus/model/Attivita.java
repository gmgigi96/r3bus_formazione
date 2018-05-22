package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

@Data
public class Attivita {
	
	private int codice;
	private String nome;
	private LocalDateTime dataOra;
	private int durata;
	
	private final Set<Allievo> allieviPrenotati;
	
	
	public Attivita() {
		allieviPrenotati = new TreeSet<>();
	}
	
	public Attivita(String nome, LocalDateTime data, int durata) {
		this();
		this.nome = nome;
		this.dataOra = data;
		this.durata = durata;
	}
	
	public void prenota(Allievo allievo) {
		allieviPrenotati.add(allievo);
	}
	
	public void annullaPrenotazione(Allievo allievo) {
		allieviPrenotati.remove(allievo);
	}
	
}
