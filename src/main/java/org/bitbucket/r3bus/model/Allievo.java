package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
@Entity
public class Allievo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long	  id;
	
	@Column(nullable=false)
	private String    nome;
	@Column(nullable=false)
	private String    cognome;
	@Email
	private String    email;
	private String    telefono;
	private LocalDate dataNascita;
	private String    luogoNascita;
	@Column(unique=true, nullable=false)
	private String    codiceFiscale;

	@ManyToMany
	private final Collection<Attivita> attivitaPrenotate;

	public Allievo() {
		attivitaPrenotate = new LinkedList<>();
	}

	public void prenotaAttivita(Attivita attivita) {
		this.attivitaPrenotate.add(attivita);
		attivita.prenota(this);
	}

	public void annullaPrenotazione(Attivita attivita) {
		attivitaPrenotate.remove(attivita);
		attivita.annullaPrenotazione(this);
	}
}
