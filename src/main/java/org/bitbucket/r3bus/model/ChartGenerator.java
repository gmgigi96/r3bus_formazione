package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartGenerator {
	
	public JFreeChart creaGraficoAttivitaGiornaliere(Map<LocalDate, Long> dati) {
		CategoryDataset dataset = this.creaDatiAttivitaGiornaliere(dati);
		JFreeChart chart = ChartFactory.createLineChart("", "", "", dataset);
		chart.removeLegend();
		
		return chart;
	}

	private CategoryDataset creaDatiAttivitaGiornaliere(Map<LocalDate, Long> dati) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(LocalDate data : dati.keySet()) {			
			dataset.addValue(dati.get(data), "", data);
		}
		
		return dataset;
	}
}
