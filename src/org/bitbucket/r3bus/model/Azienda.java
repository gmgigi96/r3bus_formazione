package org.bitbucket.r3bus.model;

import java.util.Map;

import lombok.Data;

@Data
public class Azienda {
	private String nome;
	
	private Map<String, Allievo> allievi;
	
	public Allievo getAllievo(String codiceFiscale) {
		return allievi.get(codiceFiscale);
	}
}
