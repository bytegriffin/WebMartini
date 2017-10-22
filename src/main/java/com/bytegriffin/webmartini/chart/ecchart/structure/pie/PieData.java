package com.bytegriffin.webmartini.chart.ecchart.structure.pie;

public class PieData{
	private String name;
	private String value;
	public PieData(String name,String value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}