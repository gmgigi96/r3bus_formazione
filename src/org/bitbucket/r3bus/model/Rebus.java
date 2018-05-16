package org.bitbucket.r3bus.model;

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
		
	}	
	
}
