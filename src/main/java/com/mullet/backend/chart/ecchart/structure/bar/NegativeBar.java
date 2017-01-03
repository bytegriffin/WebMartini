package com.mullet.backend.chart.ecchart.structure.bar;

import java.util.List;

public class NegativeBar {

	private String[] legend;
	private String[] yAxis;
	private List<String[]> series;

	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	public String[] getyAxis() {
		return yAxis;
	}
	public void setyAxis(String[] yAxis) {
		this.yAxis = yAxis;
	}
	public List<String[]> getSeries() {
		return series;
	}
	public void setSeries(List<String[]> series) {
		this.series = series;
	}
	
	
}
