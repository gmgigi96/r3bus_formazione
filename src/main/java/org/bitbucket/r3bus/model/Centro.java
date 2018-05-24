package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
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

	public void addAttivita(String nome, LocalDateTime dataOra, int durata) {
		Attivita a = new Attivita(nome, dataOra, durata);
		//TODO: attivita.put(key, a); inserire codice attivita
	}

	public Set<Attivita> getAttivitaDisponibili() {
		Set<Attivita> res = new HashSet<>();

		LocalDateTime now = LocalDateTime.now();
		attivita.forEach((codAttivita, attivita) -> {
			if (attivita.getDataOra().compareTo(now) > 0) {
				res.add(attivita);
			}
		});

		return res;
	}

	public Set<Attivita> getAttivitaInIntervallo(LocalDateTime intervalloTemporaleDa, LocalDateTime intervalloTemporaleA) {
		Set<Attivita> attivita = new TreeSet<>();

		this.attivita.forEach((codiceAttivita, att) -> {
			if(att.getDataOra().isAfter(intervalloTemporaleDa) && att.getDataOra().isBefore(intervalloTemporaleA)) {
				attivita.add(att);
			}
		});

		return attivita;
	}

	// metodi ausiliari per test

	void addAttivita(Attivita a) {
		attivita.put(a.getCodice(), a);
	}
}
