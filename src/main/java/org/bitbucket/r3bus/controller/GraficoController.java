package org.bitbucket.r3bus.controller;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.bitbucket.r3bus.model.controller.Rebus;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GraficoController {
	
	Rebus rebus;
	
	@RequestMapping("/grafico")
	public void plot(HttpServletResponse response ) {
		JFreeChart chart = null;
		OutputStream out;
		
		try {
			out = response.getOutputStream();
			ChartUtils.writeChartAsPNG(out, chart, 500, 500);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 response.setContentType("image/grafico.png");
	}

	public BufferedImage extractImage(JFreeChart chart, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = img.createGraphics();
		chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));

		g2.dispose();
		return img;

	}

}
