package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Attivita {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private LocalDateTime orarioInizio;
	@Column(nullable = false)
	private LocalDateTime orarioFine;

	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private final List<Allievo> allieviPrenotati;

	public Attivita() {
		allieviPrenotati = new ArrayList<>();
	}

	public Attivita(String nome, LocalDateTime inizio, LocalDateTime fine) {
		this();
		this.aggiornaParametri(nome, inizio, fine);
	}

	public void prenota(Allievo allievo) {
		if (!this.allieviPrenotati.contains(allievo)) {
			allieviPrenotati.add(allievo);
		}		
	}

	public void annullaPrenotazione(Allievo allievo) {
		allieviPrenotati.remove(allievo);
	}

	public void aggiornaParametri(String nuovoNome, LocalDateTime nuovoInizio, LocalDateTime nuovaFine) {
		this.nome = nuovoNome;
		this.orarioInizio = nuovoInizio;
		this.orarioFine = nuovaFine;
	}

	public int getNumeroAllieviPrenotati() {
		return this.allieviPrenotati.size();
	}

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attivita other = (Attivita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean overlap(LocalDateTime inizio, LocalDateTime fine) {
		return (isBetween(this.getOrarioInizio(), inizio, fine) || isBetween(this.getOrarioFine(), inizio, fine)
				|| isBetween(inizio, this.getOrarioInizio(), this.getOrarioFine())
				|| isBetween(fine, this.getOrarioInizio(), this.getOrarioFine()))
				&& !this.getOrarioInizio().equals(fine) && !this.getOrarioFine().equals(inizio);
	}

	private static boolean isBetween(LocalDateTime t, LocalDateTime inizio, LocalDateTime fine) {
		return t.compareTo(inizio) >= 0 && t.compareTo(fine) <= 0;
	}
}
