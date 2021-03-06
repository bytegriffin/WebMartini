<#include "/layout/header.ftl">  
<#include "/layout/chart.ftl">  
<@header />
<@chart />
</head>
<body style="background: #F7F7F7;" class="animated fadeIn">
	<div class="page-title">
		<div class="title_left">
			<ol class="breadcrumb">
				<li><a href="javascript:void(0)">报表管理</a></li>
				<li class="active">ECharts</li>
			</ol>
		</div>
	</div>

	<div class="clearfix"></div>

	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>动态区域图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="dynamicarealine" style="height: 350px;"></div>
			</div>
		</div>
	</div>

	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>趋势走向图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<div id="trendline" style="height: 350px;"></div>
			</div>
		</div>
	</div>

	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>堆叠折线图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="stackline" style="height: 350px;"></div>

			</div>
		</div>
	</div>

	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>分类对比柱状图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="categorybar" style="height: 350px;"></div>

			</div>
		</div>
	</div>


	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>两级柱状图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="negativebar" style="height: 350px;"></div>

			</div>
		</div>
	</div>

	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>混合线柱状图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="mixlinebar" style="height: 350px;"></div>

			</div>
		</div>
	</div>

	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>简单饼状图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="simplepie" style="height: 370px;"></div>

			</div>
		</div>
	</div>

	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>嵌套饼状图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="nestpie" style="height: 370px;"></div>

			</div>
		</div>
	</div>
	
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>南丁格尔玫瑰图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="rosepie" style="height: 370px;"></div>

			</div>
		</div>
	</div>


	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>散点图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="simplescatter" style="height: 370px;"></div>

			</div>
		</div>
	</div>

	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>基础雷达图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">

				<div id="simpleradar" style="height: 370px;"></div>

			</div>
		</div>
	</div>


	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>漏斗图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<div id="simplefunnel" style="height: 370px;"></div>
			</div>
		</div>
	</div>
	
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>仪表盘</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<div id="simplegauge" style="height: 370px;"></div>
			</div>
		</div>
	</div>
	
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>中国地图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<div id="chinamap" style="height: 370px;"></div>
			</div>
		</div>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>中国地图</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li class="pull-right"><a class="collapse-link"><i
							class="fa fa-chevron-up"></i></a></li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<div id="datachinamap" style="height: 470px;"></div>
			</div>
		</div>
	</div>

<script type="text/javascript">
$(document).ready(function() {
				$.getScript("${request.contextPath}/js/echarts/trend_line.js");
				$.getScript("${request.contextPath}/js/echarts/dynamic_area_line.js");
				$.getScript("${request.contextPath}/js/echarts/stack_line.js");
				$.getScript("${request.contextPath}/js/echarts/category_bar.js");
				$.getScript("${request.contextPath}/js/echarts/negative_bar.js");
				$.getScript("${request.contextPath}/js/echarts/mix_line_bar.js");
				$.getScript("${request.contextPath}/js/echarts/simple_pie.js");
				$.getScript("${request.contextPath}/js/echarts/nest_pie.js");
				$.getScript("${request.contextPath}/js/echarts/rose_pie.js");
				$.getScript("${request.contextPath}/js/echarts/simple_scatter.js");
				$.getScript("${request.contextPath}/js/echarts/simple_radar.js");
				$.getScript("${request.contextPath}/js/echarts/simple_funnel.js");
				$.getScript("${request.contextPath}/js/echarts/simple_gauge.js");
				$.getScript("${request.contextPath}/js/echarts/china_map.js");
				$.getScript("${request.contextPath}/js/echarts/data_china_map.js");
});
</script>
</body>
</html>