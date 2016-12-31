package com.webmartini.chart.ecchart.structure.line;

import java.util.List;

//堆叠折线图，就是每条数据都会被累加计算
public class StackLine {

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
