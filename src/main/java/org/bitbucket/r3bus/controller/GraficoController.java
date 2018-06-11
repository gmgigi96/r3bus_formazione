package org.bitbucket.r3bus.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.utils.ChartGenerator;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GraficoController {
	
	@Autowired
	Rebus rebus;
	
	@Autowired
	ChartGenerator chartGenerator;
	
	@RequestMapping("/direttore/{centro}/{mese}/statistiche/attivita_giornaliere.png")
	public void graficoAttivitaGiornaliere(@PathVariable("centro") Long codiceCentro, 
											@PathVariable("mese") String mese,
												HttpServletResponse response ) {
		LocalDate inizio = LocalDate.parse(mese + "-01");
		LocalDate fine = inizio.withDayOfMonth(inizio.lengthOfMonth());
		
		List<Number> lista = this.rebus.getNumeroAttivitaGiornaliere(codiceCentro, inizio, fine);
		
		JFreeChart chart = chartGenerator.creaGrafico(lista, inizio, fine, 0, 20);
		creaImmagineGrafico(response, chart);
	}

	@RequestMapping("/direttore/{centro}/{mese}/statistiche/prenotazioni_giornaliere.png")
	public void graficoPrenotazioniGiornaliere(@PathVariable("centro") Long codiceCentro,
												@PathVariable("mese") String mese,
													HttpServletResponse response ) {
		LocalDate inizio = LocalDate.parse(mese + "-01");
		LocalDate fine = inizio.withDayOfMonth(inizio.lengthOfMonth());
		
		List<Number> lista = this.rebus.getMediaPrenotati(codiceCentro, inizio, fine);
		
		JFreeChart chart = chartGenerator.creaGrafico(lista, inizio, fine, 0, 1);
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
