package org.bitbucket.r3bus.model;

import java.time.LocalDate;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Allievo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long	  id;

	@Column(nullable=false)
	@NotBlank
	private String    nome;
	@Column(nullable=false)
	@NotBlank
	private String    cognome;
	@Email
	private String    email;
	@Pattern(regexp = "[+0-9 ]*")
	private String    telefono;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	private String    luogoNascita;
	@Column(unique=true, nullable=false)
	@Size(min=11, max=16)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String    codiceFiscale;

	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	private final Set<Attivita> attivitaPrenotate;

	public Allievo() {
		attivitaPrenotate = new HashSet<>();
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
		if (codiceFiscale == null) {
			if (other.codiceFiscale != null)
				return false;
		} else if (!codiceFiscale.equals(other.codiceFiscale))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceFiscale == null) ? 0 : codiceFiscale.hashCode());
		return result;
	}

	
}
