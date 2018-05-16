package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

import lombok.Data;

@Data
public class Allievo {

	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	private LocalDate data;
	private String luogoDiNascita;
	private String CodiceFiscale;

	private final Collection<Attivita> attivitaPrenotate;

	
	public Allievo() {
		attivitaPrenotate = new LinkedList<>();
	}
	
	public void prenotaAttivita(Attivita attivita) {
		this.attivitaPrenotate.add(attivita);
		attivita.prenota(this);
	}

	public void annullaPrenotazione(Attivita attivita) {
		attivitaPrenotate.remove(attivita);
		attivita.annullaPrenotazione(this);
	}
}
