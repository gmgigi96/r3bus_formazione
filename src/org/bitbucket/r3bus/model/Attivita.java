package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

import lombok.Data;

@Data
public class Attivita {
	
	private int codice;
	private String nome;
	private LocalDateTime dataOra;
	private int durata;
	
	private final Collection<Allievo> allieviPrenotati;
	
	
	public Attivita() {
		allieviPrenotati = new LinkedList<>();
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
