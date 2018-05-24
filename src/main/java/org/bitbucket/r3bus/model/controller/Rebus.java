package org.bitbucket.r3bus.model.controller;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;

import lombok.Data;

@Data
public class Rebus {

	private Allievo allievoCorrente;
	private Azienda azienda;
	private Centro centroGestito;

	public void gestisciAllievo(String codiceFiscale) {
		this.allievoCorrente = azienda.getAllievo(codiceFiscale);
	}

	public void terminaGestione() {

	}

	public void prenotaAttivita(int codiceAttivita) {
		Attivita attivita = this.centroGestito.getAttivita(codiceAttivita);
		this.allievoCorrente.prenotaAttivita(attivita);
	}

	public void annullaPrenotazione(int codiceAttivita) {
		Attivita attivita = centroGestito.getAttivita(codiceAttivita);
		allievoCorrente.annullaPrenotazione(attivita);
	}

}
