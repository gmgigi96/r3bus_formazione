package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;

@Data
public class Centro {

	private final Map<Integer, Attivita> attivita;

	public Centro() {
		attivita = new HashMap<>();
	}

	public Attivita getAttivita(int codiceAttivita) {
		return attivita.get(codiceAttivita);
	}

	public Collection<Attivita> getAttivitaDisponibili() {
		// TODO: Scegliere struttura dati per res
		Collection<Attivita> res = new LinkedList<>();

		LocalDateTime now = LocalDateTime.now();
		attivita.forEach((codAttivita, attivita) -> {
			if (attivita.getDataOra().compareTo(now) > 0) {
				res.add(attivita);
			}
		});
		return res;
	}
	
	public Set<Attivita> getAttivitaInIntervallo(LocalDateTime intervalloTemporaleDa,
			LocalDateTime intervalloTemporaleA) {
		Set<Attivita> attivita = new TreeSet<>();
		
		this.attivita.forEach((codiceAttivita, att) -> {
			if(att.getDataOra().isAfter(intervalloTemporaleDa) && att.getDataOra().isBefore(intervalloTemporaleA)) {
				attivita.add(att);
			}
		});
		
		return attivita;
	}

	// test

	void addAttivita(Attivita a) {
		attivita.put(a.getCodice(), a);
	}

}
