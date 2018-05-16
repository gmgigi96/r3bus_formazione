package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
			if (attivita.getData().compareTo(now) > 0) {
				res.add(attivita);
			}
		});
		return res;
	}


	// test

	void addAttivita(Attivita a) {
		attivita.put(a.getCodice(), a);
	}

}
