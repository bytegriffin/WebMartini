package com.bytegriffin.webmartini.chart.ecchart.structure.scatter;

import java.util.List;

public class SimpleScatter {
	
	private String[] legend;
	private List<String[][]> series;

	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	public List<String[][]> getSeries() {
		return series;
	}
	public void setSeries(List<String[][]> series) {
		this.series = series;
	}

	

}
