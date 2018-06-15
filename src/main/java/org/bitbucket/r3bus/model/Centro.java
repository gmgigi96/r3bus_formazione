package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.bitbucket.r3bus.service.EmailService;

import lombok.Data;

@Data
@Entity
public class Centro implements PropertyListener {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private int capienza;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "centro_id")
	private final List<Attivita> attivita;

	public Centro() {
		attivita = new ArrayList<>();
	}

	public Centro(String nome, int capienza) {
		this();
		this.nome = nome;
		this.capienza = capienza;
	}

	/**
	 * Restituisce l'attività corrispondente al codice attivita dato per paramentro
	 * 
	 * @param codiceAttivita
	 * @return Attivita corrispondente a codiceAttivita
	 */
	public Attivita getAttivita(Long codiceAttivita) {
		for (Attivita a : attivita) {
			if (a.getId().equals(codiceAttivita)) {
				a.addPropertyListener(this);
				return a;
			}
		}

		return null;
	}

	/**
	 * Aggiunge un'attività al centro. L'attività viene aggiunta se non esiste già
	 * nel centro un'attività i cui orari si intersecano con gli orari della nuova
	 * attività che si vuole aggiungere. Sono invece possibili più attività con lo
	 * stesso nome, purchè rispetti la regola di prima.
	 * 
	 * @param nome
	 * @param inizio
	 * @param fine
	 */
	public void addAttivita(String nome, LocalDateTime inizio, LocalDateTime fine) {
		if (!overlap(inizio, fine)) {
			Attivita a = new Attivita(nome, inizio, fine);
			attivita.add(a);
			a.addPropertyListener(this);
		}
	}

	/**
	 * Verifica se esiste un'attivita nella lista delle attività che interseca i due
	 * orari dati per parametro
	 * 
	 * @param inizio
	 * @param fine
	 * @return true se esiste, false altrimenti
	 */
	private boolean overlap(LocalDateTime inizio, LocalDateTime fine) {
		for (Attivita a : this.attivita) {
			if (a.overlap(inizio, fine)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Restituisce le attività che sono disponibili nel centro da ora in poi
	 * 
	 * @return Un insieme di attività da ora in poi
	 */
	public Set<Attivita> getAttivitaDisponibili() {
		Set<Attivita> res = new HashSet<>();

		LocalDateTime now = LocalDateTime.now();
		attivita.forEach((attivita) -> {
			if (attivita.getOrarioInizio().compareTo(now) > 0) {
				res.add(attivita);
			}
		});

		return res;
	}

	/**
	 * Restituisce tutte le attività del centro in un dato intervallo
	 * 
	 * @param inizio
	 * @param fine
	 * @return Un insieme di attività in un dato intervallo
	 */
	public List<Attivita> getAttivitaInIntervallo(LocalDate inizio, LocalDate fine) {
		List<Attivita> attivita = new LinkedList<>();

		this.attivita.forEach((att) -> {
			if (att.getOrarioInizio().toLocalDate().compareTo(inizio) >= 0
					&& att.getOrarioFine().toLocalDate().compareTo(fine) <= 0) {
				attivita.add(att);
			}
		});

		return attivita;
	}

	/**
	 * Restituisce il numero di attività in un dato giorno
	 * 
	 * @param giorno
	 * @return Numero di attività in un giorno
	 */
	public long getNumeroAttivita(LocalDate giorno) {
		return attivita.stream()
				.filter((Attivita attivita) -> (attivita.getOrarioInizio().toLocalDate().equals(giorno))).count();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Centro other = (Centro) obj;
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

	public void addAttivita(Attivita a) {
		attivita.add(a);
	}

	@Override
	public void onPropertyEvent(Object source, String name, Object oldValue, Object newValue) {
		if (name.equals("attivita.aggiorna")) {
			Attivita a = (Attivita) newValue;
			for (Allievo allievo : a.getAllieviPrenotati()) {
				EmailService.sendEmail(allievo.getEmail(), "R3bus Formazione",
						a.getNome() + "\n" + a.getOrarioInizio().toString() + "\n" + a.getOrarioFine().toString());
			}
		}
	}

	// metodi ausiliari per test

	int contaAttivita() {
		return attivita.size();
	}

}
