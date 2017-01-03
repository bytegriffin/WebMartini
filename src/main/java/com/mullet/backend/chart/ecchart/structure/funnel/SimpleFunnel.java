package com.mullet.backend.chart.ecchart.structure.funnel;

import java.util.List;

public class SimpleFunnel {

	private String[] legend;
	private List<FunnelData> series;

	public String[] getLegend() {
		return legend;
	}
	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	public List<FunnelData> getSeries() {
		return series;
	}
	public void setSeries(List<FunnelData> series) {
		this.series = series;
	}
	
	
}
