package org.bitbucket.r3bus.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import org.bitbucket.r3bus.model.controller.Rebus;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GraficoController {
	
	@Autowired
	Rebus rebus;
	
	@RequestMapping("/direttore/{centro}/{mese}/statistiche/attivita_giornaliere.png")
	public void graficoAttivitaGiornaliere(@PathVariable("centro") int codiceCentro, 
											@PathVariable("mese")  @DateTimeFormat(iso = ISO.DATE) LocalDate mese,
												HttpServletResponse response ) {
		LocalDate inizio = mese.withDayOfMonth(1);
		LocalDate fine = mese.withDayOfMonth(mese.lengthOfMonth());
		rebus.setCentroGestito(codiceCentro);
		
		JFreeChart chart = rebus.creaGraficoAttivitaGiornaliere(inizio, fine);

		creaImmagineGrafico(response, chart);
	}

	@RequestMapping("/direttore/{centro}/{mese}/statistiche/prenotazioni_giornaliere.png")
	public void graficoPrenotazioniGiornaliere(@PathVariable("centro") int codiceCentro,
												@PathVariable("mese") @DateTimeFormat(iso = ISO.DATE) LocalDate mese,
													HttpServletResponse response ) {
		LocalDate inizio = mese.withDayOfMonth(1);
		LocalDate fine = mese.withDayOfMonth(mese.lengthOfMonth());
		rebus.setCentroGestito(codiceCentro);
		
		JFreeChart chart = rebus.creaGraficoPrenotazioniGiornaliere(inizio, fine);

		creaImmagineGrafico(response, chart);
	}

	private void creaImmagineGrafico(HttpServletResponse response, JFreeChart chart) {
		try {
			OutputStream out = response.getOutputStream();
			ChartUtils.writeChartAsPNG(out, chart, 800, 400);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("image/png");
	}
}
