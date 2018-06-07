package org.bitbucket.r3bus.model.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.utils.LocalDateRange;

/**
 * La classe StatisticheController genera le statistiche. Per generare le
 * statistiche è necessario settare l'intervallo temporale, con il metodo
 * {@link #setIntervallo(LocalDate, LocalDate)} e settare il centro. con il
 * metodo {@link #setCentro(long)}
 */
public class StatisticheController {

	private LocalDate inizio;
	private LocalDate fine;
	private Centro centro;
	private Azienda azienda;

	/**
	 * Crea una classe capace di generare statistiche dei centri dell'azienda data
	 * per parametro.
	 * 
	 * @param azienda
	 */
	public StatisticheController(Azienda azienda) {
		this.azienda = azienda;
	}

	/**
	 * Setta il centro di cui calcolare le statistiche.
	 * 
	 * @param codiceCentro
	 */
	public void setCentro(long codiceCentro) {
		this.centro = this.azienda.getCentro(codiceCentro);
	}

	/**
	 * Setta l'intervallo temporale entro cui calcolare le statistiche.
	 * 
	 * @param inizio
	 * @param fine
	 */
	public void setIntervallo(LocalDate inizio, LocalDate fine) {
		this.inizio = inizio;
		this.fine = fine;
	}

	/**
	 * Restituisce il numero di attività giornaliere del centro specificato
	 * ({@link #setCentro(long)}), nell'intervallo temporale scelto
	 * ({@link #setIntervallo(LocalDate, LocalDate)}).
	 * 
	 * @see #setCentro(long)
	 * @see #setIntervallo(LocalDate, LocalDate)
	 * 
	 * @return Lista del numero di attivita giornaliere nell'intervallo specificato
	 */
	public List<Long> getNumeroAttivitaGiornaliere() {
		List<Long> res = new LinkedList<>();
		for (LocalDate data : LocalDateRange.with(inizio, fine)) {
			res.add(centro.getNumeroAttivita(data));
		}
		return res;
	}

	/**
	 * Restituisce una corrispondenza attivita/numero allievi prenotati, relative ad
	 * un centro ({@link #setCentro(long)}), e ad un intervallo temporale
	 * ({@link #setIntervallo(LocalDate, LocalDate)}).
	 * 
	 * @see #setCentro(long)
	 * @see #setIntervallo(LocalDate, LocalDate)
	 * 
	 * @return Mappa attivita/numero allievi prenotati
	 */
	public Map<String, Integer> getElencoAttivita() {
		Map<String, Integer> attivita2prenotati = new HashMap<>();
		for (Attivita a : centro.getAttivitaInIntervallo(inizio, fine)) {
			attivita2prenotati.put(a.toString(), a.getNumeroAllieviPrenotati());
		}
		return attivita2prenotati;
	}

	/**
	 * Restituisce una media giornaliera di allievi prenotati alle attivita del
	 * centro scelto ({@link #setCentro(long)}, in un intervallo temporale
	 * ({@link StatisticheController#setIntervallo(LocalDate, LocalDate)}).
	 * 
	 * @see #setCentro(long)
	 * @see #setIntervallo(LocalDate, LocalDate)
	 * 
	 * @return Lista della media giornaliera di allievi prenotati alle attivita
	 */
	public List<Float> getMediaPrenotati() {
		List<Float> res = new LinkedList<>();
		for (LocalDate data : LocalDateRange.with(inizio, fine)) {
			List<Attivita> attivita = centro.getAttivitaInIntervallo(data, data);
			float mediaPrenotatiGiornaliera = mediaAllieviPrenotati(attivita);
			res.add(mediaPrenotatiGiornaliera / centro.getCapienza());
		}
		return res;
	}

	private float mediaAllieviPrenotati(List<Attivita> attivita) {
		if (attivita.isEmpty())
			return 0;
		float mediaPrenotatiGiornaliera = attivita.stream().mapToInt(Attivita::getNumeroAllieviPrenotati).sum()
				/ (float) attivita.size();
		return mediaPrenotatiGiornaliera;
	}

	// per test

	public void setCentro(Centro c) {
		this.centro = c;
	}

}
