package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.util.Collection;

public class Allievo {
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	private LocalDate data;		
	private String luogoDiNascita;
	private String CodiceFiscale;
	
	private Collection<Attivita> attivitaPrenotate;
	
	public void prenotaAttivita(Attivita attivita) {
		
	}
	
	public void annullaPrenotazione(Attivita attivita) {
		
	}
}
