package com.bytegriffin.webmartini.chart.ecchart.structure.bar;

import java.util.List;

//有两个y轴
public class MixLineBar {

	private String[] legend;
	private String[] xAxis;
	private List<String[]> series;

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
