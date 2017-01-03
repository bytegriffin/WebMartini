package com.mullet.backend.chart.ecchart.structure.bar;

import java.util.List;

public class CategoryBar {

	private String[] legend;
	private String[] xAxis;//可以是x轴
	private String[] yAxis;//也可以是y轴
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
