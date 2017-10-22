package com.bytegriffin.webmartini.chart.ecchart.structure.pie;

//嵌套pie
public class NestPie {

	private String[] legend;
	private PieData[] series;
	private PieData[] subSeries;

	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	public PieData[] getSeries() {
		return series;
	}
	public void setSeries(PieData[] series) {
		this.series = series;
	}
	public PieData[] getSubSeries() {
		return subSeries;
	}
	public void setSubSeries(PieData[] subSeries) {
		this.subSeries = subSeries;
	}

	
}
