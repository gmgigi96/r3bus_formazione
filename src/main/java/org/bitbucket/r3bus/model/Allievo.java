package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	private String    luogoNascita;
	@Column(unique=true, nullable=false)
	private String    codiceFiscale;

	@ManyToMany(mappedBy="allieviPrenotati", fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private final List<Attivita> attivitaPrenotate;

	public Allievo() {
		attivitaPrenotate = new LinkedList<>();
	}

	public Allievo(String nome, String cognome, String email, String telefono, LocalDate dataNascita, String luogoNascita, String codiceFiscale) {
		this();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.codiceFiscale = codiceFiscale;
	}

	public void prenotaAttivita(Attivita attivita) {
		this.attivitaPrenotate.add(attivita);
		attivita.prenota(this);
	}

	public void annullaPrenotazione(Attivita attivita) {
		attivitaPrenotate.remove(attivita);
		attivita.annullaPrenotazione(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allievo other = (Allievo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
