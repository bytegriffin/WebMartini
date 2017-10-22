package com.bytegriffin.webmartini.chart.chartjs.structure;

import java.util.List;

public class ChartjsData {
	
	private String[] labels;
	private List<DataSet> datasets;

	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public List<DataSet> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DataSet> datasets) {
		this.datasets = datasets;
	}

}
