package org.bitbucket.r3bus.model;

import lombok.Data;

@Data
public class Rebus {

	private Allievo allievoCorrente;
	private Azienda azienda;
	private Centro centroGestito;
	private StatisticheController statisticheController;

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
	
	public void eliminaAllievo() {
		azienda.eliminaAllievo(this.allievoCorrente);
		this.allievoCorrente = null;
	}
	
	public void addCentro(int codiceCentro) {
		Centro c = this.azienda.getCentro(codiceCentro);
		this.statisticheController.addCentro(c);
	}

}
