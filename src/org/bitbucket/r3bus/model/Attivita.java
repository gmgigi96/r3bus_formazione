package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Collection;

import lombok.Data;

@Data
public class Attivita {
	
	private String nome;
	private LocalDateTime data;
	
	private Collection<Allievo> allieviPrenotati;
	
	public void prenota(Allievo allievo) {
		allieviPrenotati.add(allievo);
	}
	
	public void annullaPrenotazione(Allievo allievo) {
		allieviPrenotati.remove(allievo);
	}
	
}
