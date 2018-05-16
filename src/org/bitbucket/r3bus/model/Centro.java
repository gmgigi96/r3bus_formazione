package org.bitbucket.r3bus.model;

import java.util.Collection;
import java.util.Map;

import lombok.Data;

@Data
public class Centro {
	
	private Map<Integer, Attivita> attivita;
	
	
	public Attivita getAttivita(int codiceAttivita) {
		return null;
	}
	
	public Collection<Attivita> getAttivitaDisponibili() {
		return null;
	}	
	
}
