package com.mullet.backend.chart.ecchart.structure.line;

public class DynamicAreaLine {
	//因为是动态数据，所以不会是数组，而是单个数据
	private String xAxis;
	private String series;


	public String getxAxis() {
		return xAxis;
	}
	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	

}
