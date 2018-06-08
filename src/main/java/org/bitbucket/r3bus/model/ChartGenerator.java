package org.bitbucket.r3bus.model;

import java.time.LocalDate;
import java.util.List;

import org.bitbucket.r3bus.utils.LocalDateRange;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import lombok.Data;

@Data
public class ChartGenerator {

	public JFreeChart creaGrafico(List<Number> dati, LocalDate inizio, LocalDate fine, double min, double max) {
		JFreeChart chart = ChartFactory.createLineChart("", "", "", this.creaDati(dati, inizio, fine));
		chart.removeLegend();
		CategoryPlot plot = chart.getCategoryPlot();
		
		//codice per ruotare i giorni
//		CategoryAxis domainAxis = plot.getDomainAxis();
//		domainAxis.setCategoryLabelPositions(
//		    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
		
		ValueAxis valueAxis = plot.getRangeAxis();
		valueAxis.setRange(min, max);
		
		return chart;
	}

	private CategoryDataset creaDati(List<Number> dati, LocalDate inizio, LocalDate fine) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int i = 0;
		for(LocalDate data : LocalDateRange.with(inizio, fine)) {
			dataset.addValue(dati.get(i), "", data.getDayOfMonth());
			i++;
		}
		
		return dataset;
	}

}
