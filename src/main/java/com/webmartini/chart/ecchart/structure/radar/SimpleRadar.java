package com.webmartini.chart.ecchart.structure.radar;

import java.util.List;

public class SimpleRadar {

	private String[] legend;
	private List<String[]> series;
	private List<Indicator> indicator;

	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	public List<String[]> getSeries() {
		return series;
	}
	public void setSeries(List<String[]> series) {
		this.series = series;
	}
	public List<Indicator> getIndicator() {
		return indicator;
	}
	public void setIndicator(List<Indicator> indicator) {
		this.indicator = indicator;
	}

}
