package org.bitbucket.r3bus.utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ChartGenerator {

	public JFreeChart creaGrafico(List<Number> dati, LocalDate inizio, LocalDate fine, double min, double max) {
		JFreeChart chart = ChartFactory.createLineChart("", "", "", this.creaDati(dati, inizio, fine));
		chart.removeLegend();
		chart.setBackgroundPaint(new Color(255,255,255,0));

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(242,250,255));
		plot.setBackgroundAlpha(0.3f);

		ValueAxis valueAxis = plot.getRangeAxis();
		valueAxis.setRange(min, max);
		
		if (max == 1.0)
			((NumberAxis)valueAxis).setNumberFormatOverride(new DecimalFormat("##0.0%"));

		return chart;
	}

	private CategoryDataset creaDati(List<Number> dati, LocalDate inizio, LocalDate fine) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int i = 0;
		for (LocalDate data : LocalDateRange.with(inizio, fine)) {
			dataset.addValue(dati.get(i), "", data.getDayOfMonth());
			i++;
		}

		return dataset;
	}

}
