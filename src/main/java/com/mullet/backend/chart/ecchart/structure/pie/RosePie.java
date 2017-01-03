package com.mullet.backend.chart.ecchart.structure.pie;

//南丁格尔玫瑰图
public class RosePie {

	private String[] legend;
	private PieData[] series;
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
	
	
}
