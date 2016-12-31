package com.webmartini.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webmartini.chart.chartjs.structure.ChartjsData;
import com.webmartini.chart.chartjs.structure.DataSet;
import com.webmartini.chart.chartjs.structure.DynamicLine;
import com.webmartini.util.DateUtil;

@Controller
public class ChartjsController {

	@RequestMapping("/chart/chartjs")
	public String echarts() {
		return "chart/chartjs";
	}

	@RequestMapping("/chart/chartjs/line")
	@ResponseBody
	public ChartjsData line(HttpServletResponse response)  {
		ChartjsData line = new ChartjsData();
		String[] labels = {"一月","二月","三月","四月","五月","六月","七月"};
		line.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		ds1.setLabel("数据1");
		String[] data1 = {"31", "74", "6", "39", "20", "85", "7"};
		ds1.setData(data1);
		list.add(ds1);
		DataSet ds2 = new DataSet();
		ds2.setLabel("数据2");
		String[] data2 = {"82", "23", "66", "9", "99", "4", "2"};
		ds2.setData(data2);
		list.add(ds2);
		line.setDatasets(list);
		return line;
	}

	
	@RequestMapping("/chart/chartjs/dynamicline")
	@ResponseBody
	public DynamicLine dynamicline(HttpServletResponse response)  {
		DynamicLine line = new DynamicLine();
		line.setLabels(DateUtil.getCurrentTime());
		line.setData1((Math.random()*10) + "");
		line.setData2((Math.random()*10) + "");
		return line;
	}
	
	
	@RequestMapping("/chart/chartjs/bar")
	@ResponseBody
	public ChartjsData bar(HttpServletResponse response)  {
		ChartjsData line = new ChartjsData();
		String[] labels = {"一月","二月","三月","四月","五月","六月","七月"};
		line.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		ds1.setLabel("数据1");
		String[] data1 = {"51", "30", "40", "28", "92", "50", "45"};
		ds1.setData(data1);
		list.add(ds1);
		DataSet ds2 = new DataSet();
		ds2.setLabel("数据2");
		String[] data2 = {"41", "56", "25", "48", "72", "34", "12"};
		ds2.setData(data2);
		list.add(ds2);
		line.setDatasets(list);
		return line;
	}
	
	
	@RequestMapping("/chart/chartjs/donut")
	@ResponseBody
	public ChartjsData donut(HttpServletResponse response)  {
		ChartjsData line = new ChartjsData();
		String[] labels = {"Dark Grey","Purple Color","Gray Color","Green Color","Blue Color"};
		line.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		String[] data1 = {"120", "50", "140", "180", "100"};
		ds1.setData(data1);
		list.add(ds1);
		line.setDatasets(list);
		return line;
	}
	
	@RequestMapping("/chart/chartjs/radar")
	@ResponseBody
	public ChartjsData radar(HttpServletResponse response)  {
		ChartjsData line = new ChartjsData();
		String[] labels = {"Eating","Drinking","Sleeping","Designing","Coding","Cycling","Running"};
		line.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		ds1.setLabel("数据1");
		String[] data1 = {"65", "59", "90", "81", "56", "55", "40"};
		ds1.setData(data1);
		list.add(ds1);
		DataSet ds2 = new DataSet();
		ds2.setLabel("数据2");
		String[] data2 = {"28", "48", "40", "19", "96", "27", "100"};
		ds2.setData(data2);
		list.add(ds2);
		line.setDatasets(list);
		return line;
	}
	
	@RequestMapping("/chart/chartjs/pie")
	@ResponseBody
	public ChartjsData pie(HttpServletResponse response)  {
		ChartjsData line = new ChartjsData();
		String[] labels = {"Dark Gray","Purple","Gray","Green","Blue"};
		line.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		ds1.setLabel("数据图");
		String[] data1 = {"120", "50", "140", "180", "100"};
		ds1.setData(data1);
		list.add(ds1);
		line.setDatasets(list);
		return line;
	}
	
	@RequestMapping("/chart/chartjs/polarArea")
	@ResponseBody
	public ChartjsData polarArea(HttpServletResponse response)  {
		ChartjsData line = new ChartjsData();
		String[] labels = {"Dark Gray","Purple","Gray","Green","Blue"};
		line.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		ds1.setLabel("数据图");
		String[] data1 = {"120", "50", "140", "180", "100"};
		ds1.setData(data1);
		list.add(ds1);
		line.setDatasets(list);
		return line;
	}
	

}
