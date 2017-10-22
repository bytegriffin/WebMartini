package com.bytegriffin.webmartini.chart.ecchart.structure.line;

import java.util.List;

public class TrendLine {

	private String title;
	private String[] legend;
	private String[] xAxis;
	private List<String[]> series;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getLegend() {
		return legend;
	}

	public void setLegend(String[] legend) {
		this.legend = legend;
	}

	public String[] getxAxis() {
		return xAxis;
	}

	public void setxAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}

	public List<String[]> getSeries() {
		return series;
	}

	public void setSeries(List<String[]> series) {
		this.series = series;
	}

}
