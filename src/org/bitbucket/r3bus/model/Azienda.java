package org.bitbucket.r3bus.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Azienda {

	private String nome;

	private final Map<String, Allievo> allievi;
	private final Map<Integer, Centro> centri;

	public Azienda() {
		allievi = new HashMap<>();
		centri = new HashMap<>();
	}

	public Allievo getAllievo(String codiceFiscale) {
		return allievi.get(codiceFiscale);
	}

	public void eliminaAllievo(Allievo allievo) {
		this.allievi.remove(allievo.getCodiceFiscale());	
	}

	public Centro getCentro(int codiceCentro) {
		return this.centri.get(codiceCentro);
	}
	
}
