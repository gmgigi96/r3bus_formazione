package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Attivita implements Comparable<Attivita> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@NotBlank
	private String nome;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@NotNull
	private LocalDateTime orarioInizio;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@NotNull
	private LocalDateTime orarioFine;

	@ManyToMany(mappedBy = "attivitaPrenotate", fetch = FetchType.EAGER)
	private final Set<Allievo> allieviPrenotati;

	@ManyToOne
	private Centro centro;

	private transient final List<PropertyListener> abbonati;

	public Attivita() {
		allieviPrenotati = new HashSet<>();
		abbonati = new LinkedList<>();
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
		informaAbbonati("attivita.aggiorna", null, this);
	}

	public int getNumeroAllieviPrenotati() {
		return this.allieviPrenotati.size();
	}

	public void addPropertyListener(PropertyListener pl) {
		this.abbonati.add(pl);
	}

	private void informaAbbonati(String eventName, Object oldValue, Object newValue) {
		for (PropertyListener pl : this.abbonati) {
			pl.onPropertyEvent(this, eventName, oldValue, newValue);
		}
	}

	@Override
	public String toString() {
		return this.nome + " (" + this.orarioInizio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")) + ")";
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

	@Override
	public int compareTo(Attivita o) {
		return this.orarioInizio.compareTo(o.getOrarioInizio());
	}
	
}
