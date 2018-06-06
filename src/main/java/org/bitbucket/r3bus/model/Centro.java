package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Centro {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private int capienza;
	
	@OneToMany
	@JoinColumn(name="centro_id")
	private final Map<Long, Attivita> attivita;

	public Centro() {
		attivita = new HashMap<>();
	}

	public Attivita getAttivita(long codiceAttivita) {
		return attivita.get(codiceAttivita);
	}

	public void addAttivita(String nome, LocalDateTime inizio, LocalDateTime fine) {
		Attivita a = new Attivita(nome, inizio, fine);
		attivita.put(a.getId(), a);
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

	public void addAttivita(Attivita a) {
		attivita.put(a.getId(), a);
	}

	int contaAttivita() {
		return attivita.size();
	}
}
