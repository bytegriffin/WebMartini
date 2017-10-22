package com.bytegriffin.webmartini.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bytegriffin.webmartini.chart.chartjs.structure.DynamicLine;
import com.bytegriffin.webmartini.util.DateUtil;


@Controller
public class SparklineController {

	@RequestMapping("/chart/sparkline")
	public String echarts() {
		return "chart/sparkline";
	}
	
	@RequestMapping("/chart/sparkline/arealine")
	@ResponseBody
	public DynamicLine arealine(HttpServletResponse response)  {
		DynamicLine line = new DynamicLine();
		line.setLabels(DateUtil.getCurrentTime());
		line.setData1((Math.random()*10) + "");
		line.setData2((Math.random()*10) + "");
		return line;
	}
	
}
