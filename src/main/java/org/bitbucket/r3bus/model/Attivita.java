package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Attivita {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@NotEmpty
	private String nome;
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime orarioInizio;
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime orarioFine;

	@ManyToMany(mappedBy="attivitaPrenotate", fetch=FetchType.EAGER)
	private final Set<Allievo> allieviPrenotati;

	public Attivita() {
		allieviPrenotati = new HashSet<>();
	}

	public Attivita(String nome, LocalDateTime inizio, LocalDateTime fine) {
		this();
		this.aggiornaParametri(nome, inizio, fine);
	}

	public void prenota(Allievo allievo) {
		allieviPrenotati.add(allievo);
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

	

	public boolean overlap(LocalDateTime inizio, LocalDateTime fine) {
		return (isBetween(this.getOrarioInizio(), inizio, fine) || isBetween(this.getOrarioFine(), inizio, fine)
				|| isBetween(inizio, this.getOrarioInizio(), this.getOrarioFine())
				|| isBetween(fine, this.getOrarioInizio(), this.getOrarioFine()))
				&& !this.getOrarioInizio().equals(fine) && !this.getOrarioFine().equals(inizio);
	}

	private static boolean isBetween(LocalDateTime t, LocalDateTime inizio, LocalDateTime fine) {
		return t.compareTo(inizio) >= 0 && t.compareTo(fine) <= 0;
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (orarioFine == null) {
			if (other.orarioFine != null)
				return false;
		} else if (!orarioFine.equals(other.orarioFine))
			return false;
		if (orarioInizio == null) {
			if (other.orarioInizio != null)
				return false;
		} else if (!orarioInizio.equals(other.orarioInizio))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((orarioFine == null) ? 0 : orarioFine.hashCode());
		result = prime * result + ((orarioInizio == null) ? 0 : orarioInizio.hashCode());
		return result;
	}
}
