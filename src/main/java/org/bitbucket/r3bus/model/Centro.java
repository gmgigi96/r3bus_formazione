package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

@Data
public class Centro {

	private final Map<Integer, Attivita> attivita;
	private int capienza;

	public Centro() {
		attivita = new HashMap<>();
	}

	public Attivita getAttivita(int codiceAttivita) {
		return attivita.get(codiceAttivita);
	}

	public void addAttivita(String nome, LocalDateTime inizio, LocalDateTime fine) {
		Attivita a = new Attivita(nome, inizio, fine);
		attivita.put(a.getCodice(), a);
	}

	public Set<Attivita> getAttivitaDisponibili() {
		Set<Attivita> res = new HashSet<>();

		LocalDateTime now = LocalDateTime.now();
		attivita.forEach((codAttivita, attivita) -> {
			if (attivita.getOrarioInizio().compareTo(now) > 0) {
				res.add(attivita);
			}
		});

		return res;
	}

	public SortedSet<Attivita> getAttivitaInIntervallo(LocalDate inizio, LocalDate fine) {
		SortedSet<Attivita> attivita = new TreeSet<>();

		this.attivita.forEach((codiceAttivita, att) -> {
			if (att.getOrarioInizio().toLocalDate().compareTo(inizio) >= 0
					&& att.getOrarioFine().toLocalDate().compareTo(fine) <= 0) {
				attivita.add(att);
			}
		});

		return attivita;
	}

	public long getNumeroAttivita(LocalDate giorno) {
		return attivita.values().stream()
				.filter((Attivita attivita) -> (attivita.getOrarioInizio().toLocalDate().equals(giorno))).count();
	}

	// metodi ausiliari per test

	void addAttivita(Attivita a) {
		attivita.put(a.getCodice(), a);
	}

	int contaAttivita() {
		return attivita.size();
	}
}
