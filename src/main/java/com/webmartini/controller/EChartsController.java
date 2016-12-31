package com.webmartini.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webmartini.chart.ecchart.structure.bar.CategoryBar;
import com.webmartini.chart.ecchart.structure.bar.MixLineBar;
import com.webmartini.chart.ecchart.structure.bar.NegativeBar;
import com.webmartini.chart.ecchart.structure.funnel.FunnelData;
import com.webmartini.chart.ecchart.structure.funnel.SimpleFunnel;
import com.webmartini.chart.ecchart.structure.gauge.SimpleGauge;
import com.webmartini.chart.ecchart.structure.line.DynamicAreaLine;
import com.webmartini.chart.ecchart.structure.line.StackLine;
import com.webmartini.chart.ecchart.structure.line.TrendLine;
import com.webmartini.chart.ecchart.structure.map.ChinaMap;
import com.webmartini.chart.ecchart.structure.map.MapData;
import com.webmartini.chart.ecchart.structure.pie.NestPie;
import com.webmartini.chart.ecchart.structure.pie.PieData;
import com.webmartini.chart.ecchart.structure.pie.RosePie;
import com.webmartini.chart.ecchart.structure.pie.SimplePie;
import com.webmartini.chart.ecchart.structure.radar.Indicator;
import com.webmartini.chart.ecchart.structure.radar.SimpleRadar;
import com.webmartini.chart.ecchart.structure.scatter.SimpleScatter;
import com.webmartini.util.DateUtil;



@Controller
public class EChartsController {

	@RequestMapping("/chart/echarts")
	public String echarts() {
		return "chart/echarts";
	}

	@RequestMapping("/chart/echarts/trendline")
	@ResponseBody
	public TrendLine trendline(HttpServletResponse response) {
		TrendLine tl = new TrendLine();
		tl.setTitle("未来一周气温变化");
		String[] legend = {"最高气温", "最低气温"};
		tl.setLegend(legend);
		String[] xAxis = {"周一", "周二" , "周三", "周四", "周五", "周六", "周日"};
		tl.setxAxis(xAxis);
		List<String[]> series = new ArrayList<String[]>();
		//第一条数据线
		String[] data1 = {"11", "11", "15", "13", "12", "13", "10"};
		series.add(data1);
		//第二条数据线
		String[] data2 = {"1", "-2", "2", "5", "3", "2", "0"};
		series.add(data2);
		tl.setSeries(series);
		return tl;
	}

	@RequestMapping("/chart/echarts/dynamicarealine")
	@ResponseBody
	public DynamicAreaLine dynamicarealine(HttpServletResponse response)  {
		DynamicAreaLine al = new DynamicAreaLine();
		al.setxAxis(DateUtil.getCurrentTime());
		int random =(int)(Math.random()*10) ;
		al.setSeries(random + "");
		return al;
	}

	@RequestMapping("/chart/echarts/stackline")
	@ResponseBody
	public StackLine stackline(HttpServletResponse response)  {
		StackLine dl = new StackLine();
		String[] legend = {"邮件营销", "联盟广告", "视频广告", "直接访问", "搜索引擎"};
		dl.setLegend(legend);
		String[] xAxis = {"周一", "周二" , "周三", "周四", "周五", "周六", "周日"};
		dl.setxAxis(xAxis);
		List<String[]> series = new ArrayList<String[]>();
		//第一条数据线
		String[] data1 = {"120", "132", "101", "134", "90", "230", "210"};
		series.add(data1);
		//第二条数据线
		String[] data2 = {"220", "182", "191", "234", "290", "330", "310"};
		series.add(data2);
		//第三条数据线
		String[] data3 = {"150", "232", "201", "154", "190", "330", "410"};
		series.add(data3);
		//第四条数据线
		String[] data4 = {"320", "332", "301", "334", "390", "330", "320"};
		series.add(data4);
		//第五条数据线
		String[] data5 = {"820", "932", "901", "934", "1290", "1330", "1320"};
		series.add(data5);
		dl.setSeries(series);
		return dl;
	}

	@RequestMapping("/chart/echarts/categorybar")
	@ResponseBody
	public CategoryBar categorybar(HttpServletResponse response)  {
		CategoryBar cb = new CategoryBar();
		String[] legend = {"蒸发量", "降水量"};
		cb.setLegend(legend);
		List<String[]> series = new ArrayList<String[]>();
		String[] xAxis = {"1月",  "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
		cb.setxAxis(xAxis);
		String[] data1 = {"2.0", "4.9", "7.0", "23.2", "25.6", "76.7", "135.6", "162.2", "32.6", "20.0", "6.4", "3.3"};
		series.add(data1);
		String[] data2 = {"2.6", "5.9", "9.0", "26.4", "28.7", "70.7", "175.6", "182.2", "48.7", "18.8", "6.0", "2.3"};
		series.add(data2);
		cb.setSeries(series);
		return cb;
	}
	
	@RequestMapping("/chart/echarts/negativebar")
	@ResponseBody
	public NegativeBar negativebar(HttpServletResponse response)  {
		NegativeBar nb = new NegativeBar();
		String[] legend = {"利润", "支出", "收入"};
		nb.setLegend(legend);
		List<String[]> series = new ArrayList<String[]>();
		String[] yAxis = {"周一","周二","周三","周四","周五","周六","周日"};
		nb.setyAxis(yAxis);
		String[] data1 = {"200", "170", "240", "244", "200", "220", "210"};
		series.add(data1);
		String[] data2 = {"320", "302", "341", "374", "390", "450", "420"};
		series.add(data2);
		String[] data3 = {"-120", "-132", "-101", "-134", "-190", "-230", "-210"};
		series.add(data3);
		nb.setSeries(series);
		return nb;
	}

	@RequestMapping("/chart/echarts/mixlinebar")
	@ResponseBody
	public MixLineBar mixlinebar(HttpServletResponse response)  {
		MixLineBar cb = new MixLineBar();
		String[] legend = {"蒸发量", "降水量","平均温度"};
		cb.setLegend(legend);
		List<String[]> series = new ArrayList<String[]>();
		String[] xAxis = {"1月",  "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
		cb.setxAxis(xAxis);
		String[] data1 = {"2.0", "4.9", "7.0", "23.2", "25.6", "76.7", "135.6", "162.2", "32.6", "20.0", "6.4", "3.3"};
		series.add(data1);
		String[] data2 = {"2.6", "5.9", "9.0", "26.4", "28.7", "70.7", "175.6", "182.2", "48.7", "18.8", "6.0", "2.3"};
		series.add(data2);
		String[] data3 = {"2.0", "2.2", "3.3", "4.5", "6.3", "10.2", "20.3", "23.4", "23.0", "16.5", "12.0", "6.2"};
		series.add(data3);
		cb.setSeries(series);
		return cb;
	}

	@RequestMapping("/chart/echarts/simplepie")
	@ResponseBody
	public SimplePie simplepie(HttpServletResponse response)  {
		SimplePie cb = new SimplePie();
		String[] legend = {"直接访问","邮件营销","联盟广告","视频广告","搜索引擎"};
		cb.setLegend(legend);
		String[] series = {"335", "310", "234", "135", "1548"};
		cb.setSeries(series);
		return cb;
	}

	@RequestMapping("/chart/echarts/nestpie")
	@ResponseBody
	public NestPie nestpie(HttpServletResponse response)  {
		NestPie np = new NestPie();
		String[] legend = {"直达","营销广告","搜索引擎","邮件营销","联盟广告","视频广告","百度","谷歌","必应","其他"};
		np.setLegend(legend);
		PieData[] series = new PieData[3];
		series[0] = new PieData("直达","335");
		series[1] = new PieData("营销广告", "679");
		series[2] = new PieData("搜索引擎", "1548");
		np.setSeries(series);

		PieData[] subseries = new PieData[8];
		subseries[0] = new PieData("直达", "335");
		subseries[1] = new PieData("邮件营销", "310");
		subseries[2] = new PieData("联盟广告", "234");
		subseries[3] = new PieData("视频广告", "135");
		subseries[4] = new PieData("百度", "1048");
		subseries[5] = new PieData("谷歌", "251");
		subseries[6] = new PieData("必应", "147");
		subseries[7] = new PieData("其他", "102");
		np.setSubSeries(subseries);
		return np;
	}

	@RequestMapping("/chart/echarts/rosepie")
	@ResponseBody
	public RosePie rosepie(HttpServletResponse response)  {
		RosePie cb = new RosePie();
		String[] legend = {"rose1","rose2","rose3","rose4","rose5","rose6","rose7","rose8"};
		cb.setLegend(legend);
		PieData[] series = new PieData[8];
		series[0] = new PieData("rose1","10");
		series[1] = new PieData("rose2", "5");
		series[2] = new PieData("rose3", "15");
		series[3] = new PieData("rose4", "25");
		series[4] = new PieData("rose5", "20");
		series[5] = new PieData("rose6", "35");
		series[6] = new PieData("rose7", "30");
		series[7] = new PieData("rose8", "40");
		cb.setSeries(series);
		return cb;
	}

	@RequestMapping("/chart/echarts/simplescatter")
	@ResponseBody
	public SimpleScatter simplescatter(HttpServletResponse response)  {
		SimpleScatter ss = new SimpleScatter();
		String[] legend = {"女性","男性"};
		ss.setLegend(legend);
		List<String[][]> series = new ArrayList<String[][]>();
		Random random = new Random();
		String[][] data1 = new String[50][2];
		for(int i=0;i<50;i++){
			data1[i][0] = random.nextInt(30)+148+"";
			data1[i][1] = random.nextInt(42)+40+"";
		}
		series.add(data1);
		String[][] data2 = new String[50][2];
		for(int i=0;i<50;i++){
			data2[i][0] = random.nextInt(40)+152+"";
			data2[i][1] = random.nextInt(55)+40+"";
		}
		series.add(data2);
		ss.setSeries(series);
		return ss;
	}

	
	@RequestMapping("/chart/echarts/simpleradar")
	@ResponseBody
	public SimpleRadar simpleradar(HttpServletResponse response)  {
		SimpleRadar al = new SimpleRadar();
		String[] legend = {"预算分配（Allocated Budget）","实际开销（Actual Spending）"};
		al.setLegend(legend);
		List<Indicator> indicator = new ArrayList<Indicator>();
		Indicator in = new Indicator();
		in.setText("销售（sales）");
		in.setMax("6500");
		indicator.add(in);
		in = new Indicator();
		in.setText("管理（Administration）");
		in.setMax("16000");
		indicator.add(in);
		in = new Indicator();
		in.setText("信息技术（Information Techology）");
		in.setMax("30000");
		indicator.add(in);
		in = new Indicator();
		in.setText("客服（Customer Support）");
		in.setMax("38000");
		indicator.add(in);
		in = new Indicator();
		in.setText("研发（Development）");
		in.setMax("52000");
		indicator.add(in);
		in = new Indicator();
		in.setText("市场（Marketing）");
		in.setMax("25000");
		indicator.add(in);
		al.setIndicator(indicator);
		List<String[]> series = new ArrayList<String[]>();
		String[] data1 = {"4300","10000","28000","35000","50000","19000"};
		series.add(data1);
		String[] data2 = {"5000","14000","28000","31000","42000","21000"};
		series.add(data2);
		al.setSeries(series);
		
		return al;
	}

	
	@RequestMapping("/chart/echarts/simplefunnel")
	@ResponseBody
	public SimpleFunnel simplefunnel(HttpServletResponse response)  {
		SimpleFunnel cb = new SimpleFunnel();
		String[] legend = {"展现","点击","联盟广告","访问","咨询","订单"};
		cb.setLegend(legend);
		List<FunnelData> funnelData = new ArrayList<FunnelData>();
		FunnelData fd = new FunnelData();
		fd.setName("访问");
		fd.setValue("60");
		funnelData.add(fd);
		fd = new FunnelData();
		fd.setName("咨询");
		fd.setValue("40");
		funnelData.add(fd);
		fd = new FunnelData();
		fd.setName("订单");
		fd.setValue("20");
		funnelData.add(fd);
		fd = new FunnelData();
		fd.setName("点击");
		fd.setValue("80");
		funnelData.add(fd);
		fd = new FunnelData();
		fd.setName("展现");
		fd.setValue("100");
		funnelData.add(fd);
		cb.setSeries(funnelData);
		return cb;
	}
	
	@RequestMapping("/chart/echarts/simplegauge")
	@ResponseBody
	public SimpleGauge simplegauge(HttpServletResponse response)  {
		SimpleGauge sg = new SimpleGauge();
		sg.setName("完成率");
		sg.setValue("60");
		return sg;
	}

	@RequestMapping("/chart/echarts/chinamap")
	@ResponseBody
	public ChinaMap chinamap(HttpServletResponse response)  {
		ChinaMap sg = new ChinaMap();
		List<MapData> data = new ArrayList<MapData>();
		MapData md = new MapData();
		md.setName("广东");
		md.setSelected(true);
		data.add(md);
		sg.setData(data);
		return sg;
	}

	@RequestMapping("/chart/echarts/datachinamap")
	@ResponseBody
	public ChinaMap datachinamap(HttpServletResponse response)  {
		ChinaMap sg = new ChinaMap();
		List<MapData> data = new ArrayList<MapData>();
		MapData md = new MapData();
		md.setName("广东");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		md = new MapData();
		md.setName("上海");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		md = new MapData();
		md.setName("天津");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		md = new MapData();
		md.setName("重庆");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		md = new MapData();
		md.setName("安徽");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		md = new MapData();
		md.setName("河北");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		md = new MapData();
		md.setName("西藏");
		md.setValue(""+(Math.random()*1000));
		data.add(md);
		sg.setData(data);
		return sg;
	}
	
	
}
