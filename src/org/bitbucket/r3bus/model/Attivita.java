package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class Attivita {
	
	private String nome;
	private LocalDate date;
	private LocalDateTime ora;
	
	private Collection<Allievo> allieviPrenotati;
	
	public void prenota(Allievo allievo) {
		
	}
	
	public void annullaPrenotazione(Allievo allievo) {
		
	}
}
