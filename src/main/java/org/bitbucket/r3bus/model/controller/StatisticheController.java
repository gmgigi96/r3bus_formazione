package org.bitbucket.r3bus.model.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.utils.LocalDateRange;

/**
 * La classe StatisticheController genera le statistiche. Per generare le
 * statistiche è necessario settare l'intervallo temporale, con il metodo
 * {@link #setIntervallo(LocalDate, LocalDate)} e settare il centro. con il
 * metodo {@link #setCentro(long)}
 */
public class StatisticheController {

	/**
	 * Crea una classe capace di generare statistiche dei centri dell'azienda data
	 * per parametro.
	 * 
	 * @param azienda
	 */
	public StatisticheController() {
	}

	/**
	 * Restituisce il numero di attività giornaliere del centro specificato
	 * ({@link #setCentro(long)}), nell'intervallo temporale scelto
	 * ({@link #setIntervallo(LocalDate, LocalDate)}).
	 * @param centro 
	 * 
	 * @see #setCentro(long)
	 * @see #setIntervallo(LocalDate, LocalDate)
	 * 
	 * @return Mappa del numero di attivita giornaliere nell'intervallo specificato
	 */
	public List<Number> getNumeroAttivitaGiornaliere(Centro centro, LocalDate inizio, LocalDate fine) {
		List<Number>  res = new ArrayList<>();
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
	public Map<String, Integer> getElencoAttivita(Centro centro, LocalDate inizio, LocalDate fine) {
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
	 * @param centro 
	 * 
	 * @see #setCentro(long)
	 * @see #setIntervallo(LocalDate, LocalDate)
	 * 
	 * @return Lista della media giornaliera di allievi prenotati alle attivita
	 */
	public List<Number> getMediaPrenotati(Centro centro, LocalDate inizio, LocalDate fine) {
		List<Number> res = new LinkedList<>();
		for (LocalDate data : LocalDateRange.with(inizio, fine)) {
			List<Attivita> attivita = centro.getAttivitaInIntervallo(data, data);
			float mediaPrenotatiGiornaliera = mediaAllieviPrenotati(attivita);
			res.add(mediaPrenotatiGiornaliera / centro.getCapienza());
		}
		return res;
	}

	private float mediaAllieviPrenotati(List<Attivita> attivita) {
		return attivita.isEmpty() ? 0
				: (float) attivita.stream().mapToInt(Attivita::getNumeroAllieviPrenotati).average().getAsDouble();
	}

}
