package org.bitbucket.r3bus.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Attivita {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	private String nome;
	@Column(nullable=false)
	private LocalDateTime orarioInizio;
	@Column(nullable=false)
	private LocalDateTime orarioFine;
	
	@ManyToMany
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
}
